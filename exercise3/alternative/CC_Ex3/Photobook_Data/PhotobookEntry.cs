using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Microsoft.WindowsAzure.StorageClient;

namespace Photobook_Data
{
    public class PhotobookEntry : Microsoft.WindowsAzure.StorageClient.TableServiceEntity
    {
        public PhotobookEntry()
        {
            PartitionKey = DateTime.UtcNow.ToString("MMddyyyy");
            RowKey = string.Format("{0:10}_{1}", DateTime.MaxValue.Ticks - DateTime.Now.Ticks, Guid.NewGuid());
        }

        public string ThumbnailURL { get; set; }
        public string BWURL { get; set; }
        public string PhotoURL { get; set; }
    }
}
