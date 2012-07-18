Summary
=======

A proof of concept sample application demonstrating the benefits of compressing serialized objects when transfered over
stressed networks with high latency, low bandwidth, and packet loss.

Details
=======

This example application demonstrates two simple servers, one that sends serialized objects via an uncompressed stream
and one that sends serialized objects via a Gzip compressed stream.

Two clients are provided that connect to their respective servers through an embedded proxy that simulates stressed
network conditions seen on some mobile networks and some networks connecting more remote international locations. 

A test is executed via an ant build that transfers a thousand randomly generated example Employee objects between the
compressed and uncompressed client and server. The duration for each transfer is logged and the test will pass if the
transfer between the client and server using compression runs fastest.

The benefits of this practice should be common knowledge by now, but for some, seeing a concrete example, real numbers,
and having a test bed to try different compression settings and network profiles was helpful.

Steps To Run
============

* Install a recent version of Apache Ant - http://ant.apache.org/manual/index.html
* ant test
 
