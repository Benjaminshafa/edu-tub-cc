<?xml version="1.0" encoding="utf-8"?>
<serviceModel xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" name="PhotoCloud" generation="1" functional="0" release="0" Id="3570ac0f-294d-491a-b629-6efe42d2c045" dslVersion="1.2.0.0" xmlns="http://schemas.microsoft.com/dsltools/RDSM">
  <groups>
    <group name="PhotoCloudGroup" generation="1" functional="0" release="0">
      <componentports>
        <inPort name="PhotoCloud_WebRole:Endpoint1" protocol="http">
          <inToChannel>
            <lBChannelMoniker name="/PhotoCloud/PhotoCloudGroup/LB:PhotoCloud_WebRole:Endpoint1" />
          </inToChannel>
        </inPort>
      </componentports>
      <settings>
        <aCS name="PhotoCloud_WebRole:DataConnectionString" defaultValue="">
          <maps>
            <mapMoniker name="/PhotoCloud/PhotoCloudGroup/MapPhotoCloud_WebRole:DataConnectionString" />
          </maps>
        </aCS>
        <aCS name="PhotoCloud_WebRole:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" defaultValue="">
          <maps>
            <mapMoniker name="/PhotoCloud/PhotoCloudGroup/MapPhotoCloud_WebRole:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" />
          </maps>
        </aCS>
        <aCS name="PhotoCloud_WebRoleInstances" defaultValue="[1,1,1]">
          <maps>
            <mapMoniker name="/PhotoCloud/PhotoCloudGroup/MapPhotoCloud_WebRoleInstances" />
          </maps>
        </aCS>
        <aCS name="PhotoCloud_WorkerRole:DataConnectionString" defaultValue="">
          <maps>
            <mapMoniker name="/PhotoCloud/PhotoCloudGroup/MapPhotoCloud_WorkerRole:DataConnectionString" />
          </maps>
        </aCS>
        <aCS name="PhotoCloud_WorkerRole:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" defaultValue="">
          <maps>
            <mapMoniker name="/PhotoCloud/PhotoCloudGroup/MapPhotoCloud_WorkerRole:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" />
          </maps>
        </aCS>
        <aCS name="PhotoCloud_WorkerRoleInstances" defaultValue="[1,1,1]">
          <maps>
            <mapMoniker name="/PhotoCloud/PhotoCloudGroup/MapPhotoCloud_WorkerRoleInstances" />
          </maps>
        </aCS>
      </settings>
      <channels>
        <lBChannel name="LB:PhotoCloud_WebRole:Endpoint1">
          <toPorts>
            <inPortMoniker name="/PhotoCloud/PhotoCloudGroup/PhotoCloud_WebRole/Endpoint1" />
          </toPorts>
        </lBChannel>
      </channels>
      <maps>
        <map name="MapPhotoCloud_WebRole:DataConnectionString" kind="Identity">
          <setting>
            <aCSMoniker name="/PhotoCloud/PhotoCloudGroup/PhotoCloud_WebRole/DataConnectionString" />
          </setting>
        </map>
        <map name="MapPhotoCloud_WebRole:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" kind="Identity">
          <setting>
            <aCSMoniker name="/PhotoCloud/PhotoCloudGroup/PhotoCloud_WebRole/Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" />
          </setting>
        </map>
        <map name="MapPhotoCloud_WebRoleInstances" kind="Identity">
          <setting>
            <sCSPolicyIDMoniker name="/PhotoCloud/PhotoCloudGroup/PhotoCloud_WebRoleInstances" />
          </setting>
        </map>
        <map name="MapPhotoCloud_WorkerRole:DataConnectionString" kind="Identity">
          <setting>
            <aCSMoniker name="/PhotoCloud/PhotoCloudGroup/PhotoCloud_WorkerRole/DataConnectionString" />
          </setting>
        </map>
        <map name="MapPhotoCloud_WorkerRole:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" kind="Identity">
          <setting>
            <aCSMoniker name="/PhotoCloud/PhotoCloudGroup/PhotoCloud_WorkerRole/Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" />
          </setting>
        </map>
        <map name="MapPhotoCloud_WorkerRoleInstances" kind="Identity">
          <setting>
            <sCSPolicyIDMoniker name="/PhotoCloud/PhotoCloudGroup/PhotoCloud_WorkerRoleInstances" />
          </setting>
        </map>
      </maps>
      <components>
        <groupHascomponents>
          <role name="PhotoCloud_WebRole" generation="1" functional="0" release="0" software="C:\Users\tim\documents\visual studio 2010\Projects\PhotoCloud\PhotoCloud\csx\Debug\roles\PhotoCloud_WebRole" entryPoint="base\x64\WaHostBootstrapper.exe" parameters="base\x64\WaIISHost.exe " memIndex="1792" hostingEnvironment="frontendadmin" hostingEnvironmentVersion="2">
            <componentports>
              <inPort name="Endpoint1" protocol="http" portRanges="80" />
            </componentports>
            <settings>
              <aCS name="DataConnectionString" defaultValue="" />
              <aCS name="Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" defaultValue="" />
              <aCS name="__ModelData" defaultValue="&lt;m role=&quot;PhotoCloud_WebRole&quot; xmlns=&quot;urn:azure:m:v1&quot;&gt;&lt;r name=&quot;PhotoCloud_WebRole&quot;&gt;&lt;e name=&quot;Endpoint1&quot; /&gt;&lt;/r&gt;&lt;r name=&quot;PhotoCloud_WorkerRole&quot; /&gt;&lt;/m&gt;" />
            </settings>
            <resourcereferences>
              <resourceReference name="DiagnosticStore" defaultAmount="[4096,4096,4096]" defaultSticky="true" kind="Directory" />
              <resourceReference name="EventStore" defaultAmount="[1000,1000,1000]" defaultSticky="false" kind="LogStore" />
            </resourcereferences>
          </role>
          <sCSPolicy>
            <sCSPolicyIDMoniker name="/PhotoCloud/PhotoCloudGroup/PhotoCloud_WebRoleInstances" />
            <sCSPolicyFaultDomainMoniker name="/PhotoCloud/PhotoCloudGroup/PhotoCloud_WebRoleFaultDomains" />
          </sCSPolicy>
        </groupHascomponents>
        <groupHascomponents>
          <role name="PhotoCloud_WorkerRole" generation="1" functional="0" release="0" software="C:\Users\tim\documents\visual studio 2010\Projects\PhotoCloud\PhotoCloud\csx\Debug\roles\PhotoCloud_WorkerRole" entryPoint="base\x64\WaHostBootstrapper.exe" parameters="base\x64\WaWorkerHost.exe " memIndex="1792" hostingEnvironment="consoleroleadmin" hostingEnvironmentVersion="2">
            <settings>
              <aCS name="DataConnectionString" defaultValue="" />
              <aCS name="Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" defaultValue="" />
              <aCS name="__ModelData" defaultValue="&lt;m role=&quot;PhotoCloud_WorkerRole&quot; xmlns=&quot;urn:azure:m:v1&quot;&gt;&lt;r name=&quot;PhotoCloud_WebRole&quot;&gt;&lt;e name=&quot;Endpoint1&quot; /&gt;&lt;/r&gt;&lt;r name=&quot;PhotoCloud_WorkerRole&quot; /&gt;&lt;/m&gt;" />
            </settings>
            <resourcereferences>
              <resourceReference name="DiagnosticStore" defaultAmount="[4096,4096,4096]" defaultSticky="true" kind="Directory" />
              <resourceReference name="EventStore" defaultAmount="[1000,1000,1000]" defaultSticky="false" kind="LogStore" />
            </resourcereferences>
          </role>
          <sCSPolicy>
            <sCSPolicyIDMoniker name="/PhotoCloud/PhotoCloudGroup/PhotoCloud_WorkerRoleInstances" />
            <sCSPolicyFaultDomainMoniker name="/PhotoCloud/PhotoCloudGroup/PhotoCloud_WorkerRoleFaultDomains" />
          </sCSPolicy>
        </groupHascomponents>
      </components>
      <sCSPolicy>
        <sCSPolicyFaultDomain name="PhotoCloud_WebRoleFaultDomains" defaultPolicy="[2,2,2]" />
        <sCSPolicyFaultDomain name="PhotoCloud_WorkerRoleFaultDomains" defaultPolicy="[2,2,2]" />
        <sCSPolicyID name="PhotoCloud_WebRoleInstances" defaultPolicy="[1,1,1]" />
        <sCSPolicyID name="PhotoCloud_WorkerRoleInstances" defaultPolicy="[1,1,1]" />
      </sCSPolicy>
    </group>
  </groups>
  <implements>
    <implementation Id="b08fdde1-2087-476a-a3e7-c1238ef98c01" ref="Microsoft.RedDog.Contract\ServiceContract\PhotoCloudContract@ServiceDefinition.build">
      <interfacereferences>
        <interfaceReference Id="a71b841b-b1c1-4089-9792-77e076ccc77e" ref="Microsoft.RedDog.Contract\Interface\PhotoCloud_WebRole:Endpoint1@ServiceDefinition.build">
          <inPort>
            <inPortMoniker name="/PhotoCloud/PhotoCloudGroup/PhotoCloud_WebRole:Endpoint1" />
          </inPort>
        </interfaceReference>
      </interfacereferences>
    </implementation>
  </implements>
</serviceModel>