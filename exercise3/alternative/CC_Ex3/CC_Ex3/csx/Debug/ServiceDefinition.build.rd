<?xml version="1.0" encoding="utf-8"?>
<serviceModel xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" name="CC_Ex3" generation="1" functional="0" release="0" Id="e0196c20-4ab3-4305-89f8-1a1668df19c5" dslVersion="1.2.0.0" xmlns="http://schemas.microsoft.com/dsltools/RDSM">
  <groups>
    <group name="CC_Ex3Group" generation="1" functional="0" release="0">
      <componentports>
        <inPort name="Photobook:Endpoint1" protocol="http">
          <inToChannel>
            <lBChannelMoniker name="/CC_Ex3/CC_Ex3Group/LB:Photobook:Endpoint1" />
          </inToChannel>
        </inPort>
      </componentports>
      <settings>
        <aCS name="Photobook:DataConnectionString" defaultValue="">
          <maps>
            <mapMoniker name="/CC_Ex3/CC_Ex3Group/MapPhotobook:DataConnectionString" />
          </maps>
        </aCS>
        <aCS name="Photobook:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" defaultValue="">
          <maps>
            <mapMoniker name="/CC_Ex3/CC_Ex3Group/MapPhotobook:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" />
          </maps>
        </aCS>
        <aCS name="Photobook_Worker:DataConnectionString" defaultValue="">
          <maps>
            <mapMoniker name="/CC_Ex3/CC_Ex3Group/MapPhotobook_Worker:DataConnectionString" />
          </maps>
        </aCS>
        <aCS name="Photobook_Worker:Microsoft.ServiceBus.ConnectionString" defaultValue="">
          <maps>
            <mapMoniker name="/CC_Ex3/CC_Ex3Group/MapPhotobook_Worker:Microsoft.ServiceBus.ConnectionString" />
          </maps>
        </aCS>
        <aCS name="Photobook_Worker:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" defaultValue="">
          <maps>
            <mapMoniker name="/CC_Ex3/CC_Ex3Group/MapPhotobook_Worker:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" />
          </maps>
        </aCS>
        <aCS name="Photobook_WorkerInstances" defaultValue="[1,1,1]">
          <maps>
            <mapMoniker name="/CC_Ex3/CC_Ex3Group/MapPhotobook_WorkerInstances" />
          </maps>
        </aCS>
        <aCS name="PhotobookInstances" defaultValue="[1,1,1]">
          <maps>
            <mapMoniker name="/CC_Ex3/CC_Ex3Group/MapPhotobookInstances" />
          </maps>
        </aCS>
      </settings>
      <channels>
        <lBChannel name="LB:Photobook:Endpoint1">
          <toPorts>
            <inPortMoniker name="/CC_Ex3/CC_Ex3Group/Photobook/Endpoint1" />
          </toPorts>
        </lBChannel>
      </channels>
      <maps>
        <map name="MapPhotobook:DataConnectionString" kind="Identity">
          <setting>
            <aCSMoniker name="/CC_Ex3/CC_Ex3Group/Photobook/DataConnectionString" />
          </setting>
        </map>
        <map name="MapPhotobook:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" kind="Identity">
          <setting>
            <aCSMoniker name="/CC_Ex3/CC_Ex3Group/Photobook/Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" />
          </setting>
        </map>
        <map name="MapPhotobook_Worker:DataConnectionString" kind="Identity">
          <setting>
            <aCSMoniker name="/CC_Ex3/CC_Ex3Group/Photobook_Worker/DataConnectionString" />
          </setting>
        </map>
        <map name="MapPhotobook_Worker:Microsoft.ServiceBus.ConnectionString" kind="Identity">
          <setting>
            <aCSMoniker name="/CC_Ex3/CC_Ex3Group/Photobook_Worker/Microsoft.ServiceBus.ConnectionString" />
          </setting>
        </map>
        <map name="MapPhotobook_Worker:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" kind="Identity">
          <setting>
            <aCSMoniker name="/CC_Ex3/CC_Ex3Group/Photobook_Worker/Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" />
          </setting>
        </map>
        <map name="MapPhotobook_WorkerInstances" kind="Identity">
          <setting>
            <sCSPolicyIDMoniker name="/CC_Ex3/CC_Ex3Group/Photobook_WorkerInstances" />
          </setting>
        </map>
        <map name="MapPhotobookInstances" kind="Identity">
          <setting>
            <sCSPolicyIDMoniker name="/CC_Ex3/CC_Ex3Group/PhotobookInstances" />
          </setting>
        </map>
      </maps>
      <components>
        <groupHascomponents>
          <role name="Photobook" generation="1" functional="0" release="0" software="C:\Users\till\documents\visual studio 2010\Projects\CC_Ex3\CC_Ex3\csx\Debug\roles\Photobook" entryPoint="base\x86\WaHostBootstrapper.exe" parameters="base\x86\WaIISHost.exe " memIndex="1792" hostingEnvironment="frontendadmin" hostingEnvironmentVersion="2">
            <componentports>
              <inPort name="Endpoint1" protocol="http" portRanges="80" />
            </componentports>
            <settings>
              <aCS name="DataConnectionString" defaultValue="" />
              <aCS name="Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" defaultValue="" />
              <aCS name="__ModelData" defaultValue="&lt;m role=&quot;Photobook&quot; xmlns=&quot;urn:azure:m:v1&quot;&gt;&lt;r name=&quot;Photobook&quot;&gt;&lt;e name=&quot;Endpoint1&quot; /&gt;&lt;/r&gt;&lt;r name=&quot;Photobook_Worker&quot; /&gt;&lt;/m&gt;" />
            </settings>
            <resourcereferences>
              <resourceReference name="DiagnosticStore" defaultAmount="[4096,4096,4096]" defaultSticky="true" kind="Directory" />
              <resourceReference name="EventStore" defaultAmount="[1000,1000,1000]" defaultSticky="false" kind="LogStore" />
            </resourcereferences>
          </role>
          <sCSPolicy>
            <sCSPolicyIDMoniker name="/CC_Ex3/CC_Ex3Group/PhotobookInstances" />
            <sCSPolicyFaultDomainMoniker name="/CC_Ex3/CC_Ex3Group/PhotobookFaultDomains" />
          </sCSPolicy>
        </groupHascomponents>
        <groupHascomponents>
          <role name="Photobook_Worker" generation="1" functional="0" release="0" software="C:\Users\till\documents\visual studio 2010\Projects\CC_Ex3\CC_Ex3\csx\Debug\roles\Photobook_Worker" entryPoint="base\x86\WaHostBootstrapper.exe" parameters="base\x86\WaWorkerHost.exe " memIndex="1792" hostingEnvironment="consoleroleadmin" hostingEnvironmentVersion="2">
            <settings>
              <aCS name="DataConnectionString" defaultValue="" />
              <aCS name="Microsoft.ServiceBus.ConnectionString" defaultValue="" />
              <aCS name="Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" defaultValue="" />
              <aCS name="__ModelData" defaultValue="&lt;m role=&quot;Photobook_Worker&quot; xmlns=&quot;urn:azure:m:v1&quot;&gt;&lt;r name=&quot;Photobook&quot;&gt;&lt;e name=&quot;Endpoint1&quot; /&gt;&lt;/r&gt;&lt;r name=&quot;Photobook_Worker&quot; /&gt;&lt;/m&gt;" />
            </settings>
            <resourcereferences>
              <resourceReference name="DiagnosticStore" defaultAmount="[4096,4096,4096]" defaultSticky="true" kind="Directory" />
              <resourceReference name="EventStore" defaultAmount="[1000,1000,1000]" defaultSticky="false" kind="LogStore" />
            </resourcereferences>
          </role>
          <sCSPolicy>
            <sCSPolicyIDMoniker name="/CC_Ex3/CC_Ex3Group/Photobook_WorkerInstances" />
            <sCSPolicyFaultDomainMoniker name="/CC_Ex3/CC_Ex3Group/Photobook_WorkerFaultDomains" />
          </sCSPolicy>
        </groupHascomponents>
      </components>
      <sCSPolicy>
        <sCSPolicyFaultDomain name="PhotobookFaultDomains" defaultPolicy="[2,2,2]" />
        <sCSPolicyFaultDomain name="Photobook_WorkerFaultDomains" defaultPolicy="[2,2,2]" />
        <sCSPolicyID name="Photobook_WorkerInstances" defaultPolicy="[1,1,1]" />
        <sCSPolicyID name="PhotobookInstances" defaultPolicy="[1,1,1]" />
      </sCSPolicy>
    </group>
  </groups>
  <implements>
    <implementation Id="1a448655-a560-40e7-b8e5-792ff8fbae71" ref="Microsoft.RedDog.Contract\ServiceContract\CC_Ex3Contract@ServiceDefinition.build">
      <interfacereferences>
        <interfaceReference Id="96d1a8d4-1e47-4c0d-ac54-a50d0f622f45" ref="Microsoft.RedDog.Contract\Interface\Photobook:Endpoint1@ServiceDefinition.build">
          <inPort>
            <inPortMoniker name="/CC_Ex3/CC_Ex3Group/Photobook:Endpoint1" />
          </inPort>
        </interfaceReference>
      </interfacereferences>
    </implementation>
  </implements>
</serviceModel>