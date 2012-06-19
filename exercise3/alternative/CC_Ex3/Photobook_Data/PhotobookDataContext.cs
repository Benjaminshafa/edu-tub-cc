using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Microsoft.WindowsAzure.StorageClient;

namespace Photobook_Data
{
    public class PhotobookDataContext : Microsoft.WindowsAzure.StorageClient.TableServiceContext
    {

        public PhotobookDataContext(string baseAddress, Microsoft.WindowsAzure.StorageCredentials credentials)
            : base(baseAddress, credentials)
        {
        }

        public IQueryable<PhotobookEntry> PhotobookEntry
        {
            get
            {
                return this.CreateQuery<PhotobookEntry>("PhotobookEntry");
            }
        }
    }
}
