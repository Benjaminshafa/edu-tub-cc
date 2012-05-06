#!/usr/bin/env python

import re;
import sys;

pattern = "";

if len(sys.argv) > 1:
	pattern = sys.argv[1];
	
counter = 0;
sumValues = 0;

while True:
	line = sys.stdin.readline();
	if not line:
		break
		
	match = re.search(pattern,line);
	
	if match != None and match.groups() > 1:
		counter += 1;
		sumValues += float(match.group(1));
		
print sumValues/counter;
