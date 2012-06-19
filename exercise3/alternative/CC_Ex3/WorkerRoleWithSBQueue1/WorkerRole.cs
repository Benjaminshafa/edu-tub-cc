using System;
using System.Drawing;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Net;
using System.Threading;
using Microsoft.ServiceBus;
using Microsoft.ServiceBus.Messaging;
using Microsoft.WindowsAzure;
using Microsoft.WindowsAzure.Diagnostics;
using Microsoft.WindowsAzure.ServiceRuntime;
using Microsoft.WindowsAzure.StorageClient;
using System.Drawing.Drawing2D;
using System.Drawing.Imaging;

using Photobook_Data;

namespace WorkerRoleWithSBQueue1
{
    public class WorkerRole : RoleEntryPoint
    {
        // The name of your queue
        const string QueueName = "photobook-queue";
        const string containerName = "photoboo-photos";

        // QueueClient is thread-safe. Recommended that you cache 
        // rather than recreating it on every request
        CloudQueue queue;
        CloudBlobContainer photosContainer;
        bool IsStopped;

        public override void Run()
        {
            while (!IsStopped)
            {
                try
                {
                    // Receive the message
                    CloudQueueMessage receivedMessage = null;
                    receivedMessage = queue.GetMessage();

                    if (receivedMessage != null)
                    {
                        // Process the message
                        Trace.WriteLine("Processing", receivedMessage.AsString);
                        ProcessImage(receivedMessage.AsString);
                        queue.DeleteMessage(receivedMessage);
                    }
                }
                catch (MessagingException e)
                {
                    if (!e.IsTransient)
                    {
                        Trace.WriteLine(e.Message);
                        throw;
                    }

                    Thread.Sleep(10000);
                }
                catch (OperationCanceledException e)
                {
                    if (!IsStopped)
                    {
                        Trace.WriteLine(e.Message);
                        throw;
                    }
                }
            }
        }

        private void ProcessImage(string imageURI)
        {
            string thumbnailBlobURI = System.Text.RegularExpressions.
                Regex.Replace(imageURI, "(.*)/([^\\.]+)(\\.[^\\.]+)?$", "$1/$2-thumb$3");
            string bwBlobURI = System.Text.RegularExpressions
                .Regex.Replace(imageURI, "(.*)/([^\\.]+)(\\.[^\\.]+)?$", "$1/$2-bw$3");

            CloudBlob inputBlob = photosContainer.GetBlobReference(imageURI);
            CloudBlob thumbnailBlob = photosContainer.GetBlobReference(thumbnailBlobURI);
            CloudBlob bwBlob = photosContainer.GetBlobReference(bwBlobURI);

            BlobStream outputThumbnail = thumbnailBlob.OpenWrite();

            ProcessThumbnail(inputBlob.OpenRead(), outputThumbnail);
            outputThumbnail.Commit();
            thumbnailBlob.Properties.ContentType = "image/jpeg";
            thumbnailBlob.SetProperties();

            BlobStream outputBW = bwBlob.OpenWrite();

            ProcessBW(inputBlob.OpenRead(), outputBW);
            outputBW.Commit();
            bwBlob.Properties.ContentType = "image/jpeg";
            bwBlob.SetProperties();

            PhotobookEntry entry = new PhotobookEntry();
            entry.PhotoURL = imageURI;
            entry.ThumbnailURL = thumbnailBlobURI;
            entry.BWURL = bwBlobURI;

            System.Diagnostics.Trace.TraceInformation("Inserted entry with uris: '{0}','{1}','{2}'", entry.PhotoURL, entry.ThumbnailURL, entry.BWURL);

            PhotobookDataSource ds = new PhotobookDataSource();
            ds.AddPhotobookEntry(entry);
        }

        private void ProcessBW(BlobStream input, BlobStream output)
        {
            var originalImage = new Bitmap(input);
            var resultImage = new Bitmap(originalImage.Width, originalImage.Height);

            for(int y = 0; y < originalImage.Height;y++){
                for (int x = 0; x < originalImage.Width; x++)
                {
                    Color color = originalImage.GetPixel(x, y);
                    int luma = (int)(color.R * 0.3 + color.G * 0.59 + color.B * 0.11);
                    resultImage.SetPixel(x, y, Color.FromArgb(luma, luma, luma));
                
                }
            }

            resultImage.Save(output, ImageFormat.Jpeg);
        }

        private void ProcessThumbnail(BlobStream input, BlobStream output)
        {
            int width;
            int height;
            var originalImage = new Bitmap(input);
            if (originalImage.Width > originalImage.Height)
            {
                width = 128;
                height = 128 * originalImage.Height / originalImage.Width;
            }
            else
            {
                height = 128;
                width = 128 * originalImage.Width / originalImage.Height;
            }
            var thumbnailImage = new Bitmap(width, height);
            using (Graphics graphics = Graphics.FromImage(thumbnailImage))
            {
                graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
                graphics.SmoothingMode = SmoothingMode.AntiAlias;
                graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
                graphics.DrawImage(originalImage, 0, 0, width, height);
            }
            thumbnailImage.Save(output, ImageFormat.Jpeg);
        }

      

        public override bool OnStart()
        {
            // Set the maximum number of concurrent connections 
            ServicePointManager.DefaultConnectionLimit = 12;

            // Create the queue if it does not exist already
            CloudStorageAccount.SetConfigurationSettingPublisher((configName, configSetter) =>
            {
                configSetter(RoleEnvironment.GetConfigurationSettingValue(configName));
            });
            var storageAccount = CloudStorageAccount.FromConfigurationSetting("DataConnectionString");

            CloudBlobClient blobStorage = storageAccount.CreateCloudBlobClient();

            photosContainer = blobStorage.GetContainerReference(containerName);
           
            queue = storageAccount.CreateCloudQueueClient().GetQueueReference(QueueName);
            Trace.TraceInformation("Creating container and queue...");

            bool storageInitialized = false;

            while (!storageInitialized)
            {
                try
                {
                    if (photosContainer.CreateIfNotExist())
                    {
                        var permissions = photosContainer.GetPermissions();
                        permissions.PublicAccess = BlobContainerPublicAccessType.Container;
                        photosContainer.SetPermissions(permissions);
                    }

                    queue.CreateIfNotExist();
                    storageInitialized = true;
                }
                catch (StorageClientException e)
                {
                    Trace.WriteLine(e.Message);
                    System.Threading.Thread.Sleep(5000);
                }
            }

            // Initialize the connection to Service Bus Queue
            IsStopped = false;
            return base.OnStart();
        }

        public override void OnStop()
        {
            // Close the connection to Service Bus Queue
            IsStopped = true;
            base.OnStop();
        }
    }
}
