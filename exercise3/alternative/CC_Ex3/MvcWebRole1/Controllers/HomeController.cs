using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Microsoft.WindowsAzure.StorageClient;
using System.Diagnostics;
using System.Collections;
using Microsoft.WindowsAzure;
using System.IO;

using Photobook_Data;

namespace MvcWebRole1.Controllers
{
    public class HomeController : Controller
    {
        //
        // GET: /Home/

        private static string containerName = "photobook-photos";
        private static string queueName = "photobook-queue";

        public ActionResult Index()
        {
            ViewBag.Message = "Welcome to PhotoShare!";
          
            return View();
        }

        public ActionResult Photos()
        {
            PhotobookDataSource ds = new PhotobookDataSource();

            IEnumerable<PhotobookEntry> entries = ds.GetPhotobookEntries();

            ArrayList imageUriList = new ArrayList();

            foreach (PhotobookEntry entry in entries)
            {
                imageUriList.Add(entry);
                ViewBag.entries = imageUriList;
            }

            return View();
        }

        public ActionResult Upload(HttpPostedFileBase file)
        {
            if (file != null && file.ContentLength > 0)
            {
                CloudBlobContainer blobContainer = GetImageContainer(containerName);
                Guid imageGuid = System.Guid.NewGuid();

                string uniqueBlobName = string.Format("photobookpics/image_{0}{1}", imageGuid.ToString(), Path.GetExtension(file.FileName));
                CloudBlob blob = blobContainer.GetBlobReference(uniqueBlobName);
                blob.Properties.ContentType = "image/jpeg";
                blob.UploadFromStream(file.InputStream);

                CloudQueue queue = GetQueue(queueName);
                var message = new CloudQueueMessage(String.Format("{0}", blob.Uri.ToString()));
                queue.AddMessage(message);
            }

            return RedirectToAction("Index");
        }

        private CloudBlobContainer GetImageContainer(string container)
        {
            CloudStorageAccount storageAccount = CloudStorageAccount.FromConfigurationSetting("DataConnectionString");
            CloudBlobClient blobClient = storageAccount.CreateCloudBlobClient();
            CloudBlobContainer rawImageContainer = blobClient.GetContainerReference(container);

            if (rawImageContainer.CreateIfNotExist())
            {
                rawImageContainer.SetPermissions(new BlobContainerPermissions { PublicAccess = BlobContainerPublicAccessType.Blob });
            }
            return rawImageContainer;
        }

        private CloudQueue GetQueue(string queueName)
        {
            CloudStorageAccount storageAccount = CloudStorageAccount.FromConfigurationSetting("DataConnectionString");
            CloudQueueClient queueStorage = storageAccount.CreateCloudQueueClient();
            CloudQueue queue = queueStorage.GetQueueReference(queueName);

            queue.CreateIfNotExist();

            return queue;
        }

        public ActionResult About()
        {
            return View();
        }

    }
}
