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
            ViewBag.Message = "Welcome to PhotoShare!";

            try
            {
                CloudBlobContainer imageContainer = GetRawImageContainer();
                IEnumerable<IListBlobItem> images = imageContainer.ListBlobs();

                ArrayList imageUriList = new ArrayList();

                foreach (IListBlobItem image in images)
                {
                    string thumbnailUri = image.Uri.AbsoluteUri;
                    imageUriList.Add(thumbnailUri);
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
                CloudBlobContainer blobContainer = GetRawImageContainer();
                Guid imageGuid = System.Guid.NewGuid();

                CloudBlob blob = blobContainer.GetBlobReference(imageGuid.ToString());
                blob.Properties.ContentType = "image/jpeg";
                blob.UploadFromStream(file.InputStream);
            }

            return RedirectToAction("Index");
        }

        private CloudBlobContainer GetRawImageContainer()
        {
            CloudStorageAccount storageAccount = CloudStorageAccount.FromConfigurationSetting("DataConnectionString");
            CloudBlobClient blobClient = CloudStorageAccountStorageClientExtensions.CreateCloudBlobClient(storageAccount);
            CloudBlobContainer rawImageContainer = blobClient.GetContainerReference("rawimages");

            if (rawImageContainer.CreateIfNotExist())
            {
                rawImageContainer.SetPermissions(new BlobContainerPermissions { PublicAccess = BlobContainerPublicAccessType.Blob });
            }
            return rawImageContainer;
        }

        public ActionResult About()
        {
            return View();
        }
    }
}
