# R script to create plots for benchmarks
# Execute with:
# R BATCH CMD plots.r
#

# header names
# instanceType,kflops
flops <- read.table("flops.csv", header=TRUE, sep=",")

# header names
# instanceType,sweepTime
memsweep <- read.table("memsweep.csv", header=TRUE, sep=",")

# header names
# instanceType,syscallTime
syscall <- read.table("syscall.csv", header=TRUE, sep=",")

# header names
# instanceType,forkTime
fork <- read.table("fork.csv", header=TRUE, sep=",")

# header names
# instanceType,diskThroughput
disk <- read.table("disk.csv", header=TRUE, sep=",")

# header names
# instanceType,networkThroughput
network <- read.table("network.csv", header=TRUE, sep=",")

as.barplot.matrix <- function(row_names, data) {
  mat <- as.matrix(data)
  rownames(mat) <- row_names
  return(mat)
}

# fonts
fontFamily <- "serif"
fontSize <- 8

# ‘c(bottom, left, top, right)’ which gives the number of lines of margin to be specified on
# the four sides of the plot.
marValue <- c(3, 4, 1, 1)

# The margin line (in ‘mex’ units) for the axis title, axis
# labels and axis line.  Note that ‘mgp[1]’ affects ‘title’
# whereas ‘mgp[2:3]’ affect ‘axis’.  The default is ‘c(3, 1, 0)’.
mgpValue <- c(1.3, 0.4, 0)

gridColor <- rgb(0.2,0.2,0.2)

pdf("plots.pdf", version="1.4")
par(family=fontFamily, ps=fontSize, mfrow=c(3, 2), mar=marValue, mgp=mgpValue)

barplot(as.barplot.matrix(flops$instanceType, flops$kflops)[,1], space=2, xlab="Instance Type", ylab="KFLOPS", border = NA)
grid(col=gridColor)

barplot(as.barplot.matrix(memsweep$instanceType, memsweep$sweepTime)[,1], space=2, xlab="Instance Type", ylab="Sweep Time [s]", border = NA)
grid(col=gridColor)

barplot(as.barplot.matrix(syscall$instanceType, syscall$syscallTime)[,1], space=2, xlab="Instance Type", ylab="Time For 50M Syscalls [s]", border = NA)
grid(col=gridColor)

barplot(as.barplot.matrix(fork$instanceType, fork$forkTime)[,1], space=2, xlab="Instance Type", ylab="Time For 1M fork() calls [s]", border = NA)
grid(col=gridColor)

barplot(as.barplot.matrix(disk$instanceType, disk$diskThroughput)[,1], space=2, xlab="Instance Type", ylab="Disk Read Performance [MB/s]", border = NA)
grid(col=gridColor)


network_matrix <- matrix(as.numeric(c(network[1,seq(2,8)], network[2,seq(2,8)], network[3,seq(2,8)])), 7)
# colnames(network_matrix) <- network[,1]

barplot(network_matrix, beside=TRUE, names.arg=c("novirt","small", "large"), xlab="Instance Type", ylab="Network Throughput [MBits/s]", border = NA)
grid(col=gridColor)

op <- par(bg="white")

par(op)
dev.off()