using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Net;
using System.Threading;
using Microsoft.WindowsAzure;
using Microsoft.WindowsAzure.Diagnostics;
using Microsoft.WindowsAzure.ServiceRuntime;
using Microsoft.WindowsAzure.StorageClient;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Imaging;
using System.IO;

namespace GuestBook_WorkerRole
{
    public class WorkerRole : RoleEntryPoint
    {
        private CloudQueue queue;
        private CloudBlobContainer rawContainer;
        private CloudBlobContainer convertedContainer;

        public override void Run()
        {
            Trace.TraceInformation("Listening for queue messages...");

            while (false)
            {
                try
                {
                    // retrieve a new message from the queue
                    CloudQueueMessage msg = queue.GetMessage();
                    if (msg != null)
                    {
                        var imageBlobUri = msg.AsString;

                        string thumbnailBlobUri = System.Text.RegularExpressions.Regex.Replace(imageBlobUri, "([^\\.]+)(\\.[^\\.]+)?$", "$1-thumb$2");
                        string grayscaleBlobUri = System.Text.RegularExpressions.Regex.Replace(imageBlobUri, "([^\\.]+)(\\.[^\\.]+)?$", "$1-gray$2");

                        CloudBlob inputBlob = rawContainer.GetBlobReference(imageBlobUri);
                        CloudBlob outputBlobThumb = convertedContainer.GetBlobReference(thumbnailBlobUri);
                        CloudBlob outputBlobGray = convertedContainer.GetBlobReference(grayscaleBlobUri);

                         using (BlobStream input = inputBlob.OpenRead())
                          using (BlobStream outputThumb = outputBlobThumb.OpenWrite())
                          using (BlobStream outputGray = outputBlobGray.OpenWrite())
                         {
                             ProcessColor(input, outputThumb);

                            // commit the blob and set its properties
                             outputThumb.Commit();
                             outputBlobThumb.Properties.ContentType = "image/jpeg";
                             outputBlobThumb.SetProperties();

                         //    ProcessGrayscale(input, outputGray);
                          //   commit the blob and set its properties
                             outputGray.Commit();
                             outputBlobGray.Properties.ContentType = "image/jpeg";
                            outputBlobGray.SetProperties();

                            // remove message from queue
                            queue.DeleteMessage(msg);

                           //  Trace.TraceInformation("Generated thumbnail in blob '{0}'.", thumbnailBlobUri);
                            }
                    }
                    else
                    {
                        System.Threading.Thread.Sleep(1000);
                    }
                }
                catch (StorageClientException e)
                {
                    Trace.TraceError("Exception when processing queue item. Message: '{0}'", e.Message);
                    System.Threading.Thread.Sleep(5000);
                }
            }

        }

        public override bool OnStart()
        {
            // Set the maximum number of concurrent connections 
            ServicePointManager.DefaultConnectionLimit = 12;

            // For information on handling configuration changes
            // see the MSDN topic at http://go.microsoft.com/fwlink/?LinkId=166357.
            // read storage account configuration settings
            CloudStorageAccount.SetConfigurationSettingPublisher((configName, configSetter) =>
            {
                configSetter(RoleEnvironment.GetConfigurationSettingValue(configName));
            });
            var storageAccount = CloudStorageAccount.FromConfigurationSetting("DataConnectionString");

            // initialize blob storage
            CloudBlobClient blobStorage = storageAccount.CreateCloudBlobClient();
            rawContainer = blobStorage.GetContainerReference("rawimages");

            convertedContainer = blobStorage.GetContainerReference("convertedImages");

            // initialize queue storage 
            CloudQueueClient queueStorage = storageAccount.CreateCloudQueueClient();
            queue = queueStorage.GetQueueReference("thumbnails");

            bool storageInitialized = false;
            while (!storageInitialized)
            {
                try
                {
                    // create the blob container and allow public access
                    rawContainer.CreateIfNotExist();
                    var permissions = rawContainer.GetPermissions();
                    permissions.PublicAccess = BlobContainerPublicAccessType.Container;
                    rawContainer.SetPermissions(permissions);

                    // create the blob container and allow public access
                    convertedContainer.CreateIfNotExist();
                    var convPermissions = convertedContainer.GetPermissions();
                    convPermissions.PublicAccess = BlobContainerPublicAccessType.Container;
                    convertedContainer.SetPermissions(permissions);

                    // create the message queue(s)
                    queue.CreateIfNotExist();

                    storageInitialized = true;
                }
                catch (StorageClientException e)
                {
                    if (e.ErrorCode == StorageErrorCode.TransportError)
                    {
                        Trace.TraceError("Storage services initialization failure. "
                          + "Check your storage account configuration settings. If running locally, "
                          + "ensure that the Development Storage service is running. Message: '{0}'", e.Message);
                        System.Threading.Thread.Sleep(5000);
                    }
                    else
                    {
                        throw;
                    }
                }
            }


            return base.OnStart();
        }

        public void ProcessColor(Stream input, Stream output)
        {
            int width;
            int height;
            var originalImage = new Bitmap(input);

            const int MAX_DIMEN = 200;

            if (originalImage.Width > originalImage.Height)
            {
                width = MAX_DIMEN;
                height = MAX_DIMEN * originalImage.Height / originalImage.Width;
            }
            else
            {
                height = MAX_DIMEN;
                width = MAX_DIMEN * originalImage.Width / originalImage.Height;
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

        public void ProcessGrayscale(Stream input, Stream output)
        {
            Bitmap original = new Bitmap(input);

            //create a blank bitmap the same size as original
            Bitmap newBitmap = new Bitmap(original.Width, original.Height);

            //get a graphics object from the new image
            Graphics g = Graphics.FromImage(newBitmap);

            //create the grayscale ColorMatrix
            ColorMatrix colorMatrix = new ColorMatrix(
               new float[][] 
                  {
                     new float[] {.3f, .3f, .3f, 0, 0},
                     new float[] {.59f, .59f, .59f, 0, 0},
                     new float[] {.11f, .11f, .11f, 0, 0},
                     new float[] {0, 0, 0, 1, 0},
                     new float[] {0, 0, 0, 0, 1}
                  });

            //create some image attributes
            ImageAttributes attributes = new ImageAttributes();

            //set the color matrix attribute
            attributes.SetColorMatrix(colorMatrix);

            //draw the original image on the new image
            //using the grayscale color matrix
            g.DrawImage(original, new Rectangle(0, 0, original.Width, original.Height),
               0, 0, original.Width, original.Height, GraphicsUnit.Pixel, attributes);

            //dispose the Graphics object
            g.Dispose();

            newBitmap.Save(output, ImageFormat.Jpeg);
        }
    }
}