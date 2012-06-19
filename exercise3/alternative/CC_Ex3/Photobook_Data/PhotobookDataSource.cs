using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Microsoft.WindowsAzure;
using Microsoft.WindowsAzure.StorageClient;

namespace Photobook_Data
{
    public class PhotobookDataSource
    {
        private static CloudStorageAccount storageAccount;
        private PhotobookDataContext context;

        static PhotobookDataSource()
        {
            storageAccount = CloudStorageAccount.FromConfigurationSetting("DataConnectionString");
            CloudTableClient.CreateTablesFromModel(typeof(PhotobookDataContext),
                storageAccount.TableEndpoint.AbsoluteUri, 
                storageAccount.Credentials);
        }

        public PhotobookDataSource()
        {
            this.context = new PhotobookDataContext(storageAccount.TableEndpoint.AbsoluteUri,
                storageAccount.Credentials);
            this.context.RetryPolicy = RetryPolicies.Retry(3, TimeSpan.FromSeconds(1));
        }

        public IEnumerable<PhotobookEntry> GetPhotobookEntries()
        {
            var results = from g in this.context.PhotobookEntry
                                    select g;
            return results;
        }

        public void AddPhotobookEntry(PhotobookEntry newEntry)
        {
            this.context.AddObject("PhotobookEntry", newEntry);
            this.context.SaveChanges();
        }
        
    }
}
