using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Microsoft.WindowsAzure.StorageClient;
using Microsoft.WindowsAzure;
using System.Collections;
using System.Diagnostics;

namespace MvcWebRole1.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            ViewBag.Message = "Welcome to PhotoCloud!";

            try
            {
                CloudBlobContainer imageContainer = GetImageContainer("rawimages");
                IEnumerable<IListBlobItem> images = imageContainer.ListBlobs();

                ArrayList imageUriList = new ArrayList();

                foreach (IListBlobItem image in images)
                {
                    string rawUri = image.Uri.AbsoluteUri;
                        
                        Dictionary<String, String> uris = new Dictionary<String, String>();
                        uris.Add("thumb", rawUri.Replace("rawimages", "convertedimages") + "-thumb");
                        uris.Add("gray", rawUri.Replace("rawimages", "convertedimages") + "-gray");
                        uris.Add("raw", rawUri);
                        imageUriList.Add(uris);
                    
                }

                ViewBag.images = imageUriList;

            }
            catch (StorageClientException ex)
            {
                ViewBag.images = null;
                Trace.WriteLine(ex.Message);
            }

            return View();
        }

        public ActionResult Upload(HttpPostedFileBase file)
        {
            if (file != null && file.ContentLength > 0)
            {
                CloudBlobContainer blobContainer = GetImageContainer("rawimages");
                Guid imageGuid = System.Guid.NewGuid();

                CloudBlob blob = blobContainer.GetBlobReference(imageGuid.ToString());
                blob.Properties.ContentType = "image/jpeg";
                blob.UploadFromStream(file.InputStream);

                CloudQueue queue = getQueue();
                var message = new CloudQueueMessage(blob.Uri.ToString());

                queue.AddMessage(message);
            }

            return RedirectToAction("Index");
        }

        private CloudBlobContainer GetImageContainer(string reference)
        {
            CloudStorageAccount storageAccount = CloudStorageAccount.FromConfigurationSetting("DataConnectionString");
            CloudBlobClient blobClient = CloudStorageAccountStorageClientExtensions.CreateCloudBlobClient(storageAccount);
            CloudBlobContainer rawImageContainer = blobClient.GetContainerReference(reference);

            if (rawImageContainer.CreateIfNotExist())
            {
                rawImageContainer.SetPermissions(new BlobContainerPermissions { PublicAccess = BlobContainerPublicAccessType.Blob });
            }
            return rawImageContainer;
        }

        private CloudQueue getQueue()
        {
            CloudStorageAccount storageAccount = CloudStorageAccount.FromConfigurationSetting("DataConnectionString");
            CloudQueueClient queueStorage = storageAccount.CreateCloudQueueClient();
            CloudQueue queue = queueStorage.GetQueueReference("queue");
            // create the message queue(s)
            queue.CreateIfNotExist();
            return queue;

        }

        public ActionResult About()
        {
            return View();
        }


    }
}