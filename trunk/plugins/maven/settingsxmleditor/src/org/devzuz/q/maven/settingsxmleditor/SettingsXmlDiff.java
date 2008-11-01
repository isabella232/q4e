package org.devzuz.q.maven.settingsxmleditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.maven.settings.Activation;
import org.apache.maven.settings.ActivationFile;
import org.apache.maven.settings.ActivationOS;
import org.apache.maven.settings.ActivationProperty;
import org.apache.maven.settings.Mirror;
import org.apache.maven.settings.Profile;
import org.apache.maven.settings.Repository;
import org.apache.maven.settings.RepositoryPolicy;
import org.apache.maven.settings.Server;
import org.apache.maven.settings.Settings;

/**
 * This class will compare 2 org.apache.maven.settings.Settings instance. Only essential elements are compared here.
 * Unessential elements like name are disregarded because it's not needed in builds. Proxies are also disregarded
 * because it is depreciated.
 * 
 * @author Allan Ramirez
 */
public class SettingsXmlDiff
{
    private Settings originalSettings;

    private Settings modifiedSettings;

    public SettingsXmlDiff( Settings originalSettings, Settings modifiedSettings )
    {
        this.originalSettings = originalSettings;
        this.modifiedSettings = modifiedSettings;
    }

    public boolean hasEssentialDiffs()
    {
        if ( !isStringEqual( originalSettings.getLocalRepository(), modifiedSettings.getLocalRepository() )
            || originalSettings.isOffline() != modifiedSettings.isOffline()
            || originalSettings.getServers().size() != modifiedSettings.getServers().size()
            || originalSettings.getMirrors().size() != modifiedSettings.getMirrors().size()
            || originalSettings.getProfiles().size() != modifiedSettings.getProfiles().size()
            || originalSettings.getActiveProfiles().size() != modifiedSettings.getActiveProfiles().size()
            || originalSettings.getPluginGroups().size() != modifiedSettings.getPluginGroups().size()
            || !getAddedOrModifiedServers().isEmpty() || !getAddedOrModifiedActiveProfiles().isEmpty()
            || !getAddedOrModifiedMirrors().isEmpty() || !getAddedOrModifiedPluginGroups().isEmpty()
            || !getAddedOrModifiedProfile().isEmpty() )
        {
            return true;
        }

        return false;
    }

    @SuppressWarnings( "unchecked" )
    public List<Server> getAddedOrModifiedServers()
    {
        List<Server> list = new ArrayList<Server>();

        List<Server> originalServers = originalSettings.getServers();
        List<Server> modifiedServers = modifiedSettings.getServers();

        Map<String, Server> originalServerMap = new HashMap<String, Server>();
        Map<String, Server> modifiedServerMap = new HashMap<String, Server>();

        for ( Server server : originalServers )
        {
            originalServerMap.put( server.getId().trim(), server );
        }

        for ( Server server : modifiedServers )
        {
            modifiedServerMap.put( server.getId().trim(), server );
        }

        for ( Map.Entry<String, Server> entry : modifiedServerMap.entrySet() )
        {
            Server server1 = entry.getValue();
            Server server2 = originalServerMap.get( entry.getKey() );

            if ( server2 == null || !isStringEqual( server1.getUsername(), server2.getUsername() )
                || !isStringEqual( server1.getPassword(), server2.getPassword() )
                || !isStringEqual( server1.getPrivateKey(), server2.getPrivateKey() )
                || !isStringEqual( server1.getPassphrase(), server2.getPassphrase() )
                || !isStringEqual( server1.getFilePermissions(), server2.getFilePermissions() )
                || !isStringEqual( server1.getDirectoryPermissions(), server2.getDirectoryPermissions() ) )
            {
                list.add( server1 );
            }
        }

        return list;
    }

    @SuppressWarnings( "unchecked" )
    public List<Mirror> getAddedOrModifiedMirrors()
    {
        List<Mirror> list = new ArrayList<Mirror>();

        List<Mirror> originalMirrors = originalSettings.getMirrors();
        List<Mirror> modifiedMirrors = modifiedSettings.getMirrors();

        Map<String, Mirror> originalMirrorMap = new HashMap<String, Mirror>();
        Map<String, Mirror> modifiedMirrorMap = new HashMap<String, Mirror>();

        for ( Mirror mirror : originalMirrors )
        {
            originalMirrorMap.put( mirror.getId().trim(), mirror );
        }

        for ( Mirror mirror : modifiedMirrors )
        {
            modifiedMirrorMap.put( mirror.getId().trim(), mirror );
        }

        for ( Map.Entry<String, Mirror> entry : modifiedMirrorMap.entrySet() )
        {
            Mirror mirror1 = entry.getValue();
            Mirror mirror2 = originalMirrorMap.get( entry.getKey() );

            if ( mirror2 == null || !isStringEqual( mirror1.getMirrorOf(), mirror2.getMirrorOf() )
                || !isStringEqual( mirror1.getUrl(), mirror2.getUrl() ) )
            {
                list.add( mirror1 );
            }
        }

        return list;
    }

    @SuppressWarnings( "unchecked" )
    public List<String> getAddedOrModifiedActiveProfiles()
    {
        List<String> list = new ArrayList<String>();

        List<String> activeProfiles1 = originalSettings.getActiveProfiles();
        List<String> activeProfiles2 = modifiedSettings.getActiveProfiles();

        Map<String, String> originalActiveProfilesMap = new HashMap<String, String>();
        Map<String, String> modifiedActiveProfilesMap = new HashMap<String, String>();

        for ( String activeProfile : activeProfiles1 )
        {
            originalActiveProfilesMap.put( activeProfile.trim(), activeProfile );
        }

        for ( String activeProfile : activeProfiles2 )
        {
            modifiedActiveProfilesMap.put( activeProfile.trim(), activeProfile );
        }

        for ( Map.Entry<String, String> entry : modifiedActiveProfilesMap.entrySet() )
        {
            String activeProfile1 = entry.getValue();
            String activeProfile2 = originalActiveProfilesMap.get( entry.getKey() );

            if ( activeProfile2 == null || !isStringEqual( activeProfile1, activeProfile2 ) )
            {
                list.add( activeProfile1 );
            }
        }
        return list;
    }

    @SuppressWarnings( "unchecked" )
    public List<String> getAddedOrModifiedPluginGroups()
    {
        List<String> list = new ArrayList<String>();

        List<String> originalPluginGroups = originalSettings.getPluginGroups();
        List<String> modifiedPluginGroups = modifiedSettings.getPluginGroups();

        Map<String, String> originalPluginGroupMap = new HashMap<String, String>();
        Map<String, String> modifiedPluginGroupMap = new HashMap<String, String>();

        for ( String pluginGroup : originalPluginGroups )
        {
            originalPluginGroupMap.put( pluginGroup.trim(), pluginGroup );
        }

        for ( String pluginGroup : modifiedPluginGroups )
        {
            modifiedPluginGroupMap.put( pluginGroup.trim(), pluginGroup );
        }

        for ( Map.Entry<String, String> entry : modifiedPluginGroupMap.entrySet() )
        {
            String pluginGroup1 = entry.getValue();
            String pluginGroup2 = originalPluginGroupMap.get( entry.getKey() );

            if ( pluginGroup2 == null || !isStringEqual( pluginGroup1, pluginGroup2 ) )
            {
                list.add( pluginGroup1 );
            }
        }

        return list;
    }

    @SuppressWarnings( "unchecked" )
    public Map<String, Profile> getAddedOrModifiedProfile()
    {
        Map<String, Profile> map = new HashMap<String, Profile>();

        Map<String, Profile> originalProfileMap = originalSettings.getProfilesAsMap();
        Map<String, Profile> modifiedProfileMap = modifiedSettings.getProfilesAsMap();

        for ( Map.Entry<String, Profile> entry : modifiedProfileMap.entrySet() )
        {
            Profile profile1 = entry.getValue();
            Profile profile2 = originalProfileMap.get( entry.getKey() );

            if ( profile2 == null || hasActivationDiff( profile1.getActivation(), profile2.getActivation() )
                || hasProfilePropertiesDiff( profile1.getProperties(), profile2.getProperties() )
                || hasProfileRepositoriesDiff( profile2, profile1 )
                || hasProfilePluginRepositoriesDiff( profile2, profile1 ) )
            {
                map.put( entry.getKey(), profile1 );
            }

        }

        return map;
    }

    @SuppressWarnings( "unchecked" )
    private boolean hasProfilePluginRepositoriesDiff( Profile originalProfile, Profile modifiedProfile )
    {
        return originalProfile.getPluginRepositories().size() != modifiedProfile.getPluginRepositories().size() ? true
                        : !getAddedorModifiedRepository( originalProfile.getPluginRepositories(),
                                                         modifiedProfile.getPluginRepositories() ).isEmpty();
    }

    @SuppressWarnings( "unchecked" )
    private boolean hasProfileRepositoriesDiff( Profile originalProfile, Profile modifiedProfile )
    {
        return originalProfile.getRepositories().size() != modifiedProfile.getRepositories().size() ? true
                        : !getAddedorModifiedRepository( originalProfile.getRepositories(),
                                                         modifiedProfile.getRepositories() ).isEmpty();
    }

    private List<Repository> getAddedorModifiedRepository( List<Repository> originalRepositories,
                                                           List<Repository> modifiedRepositories )
    {
        List<Repository> list = new ArrayList<Repository>();

        Map<String, Repository> originalRepositoryMap = new HashMap<String, Repository>();
        Map<String, Repository> modifiedRepositoryMap = new HashMap<String, Repository>();

        for ( Repository repository : originalRepositories )
        {
            originalRepositoryMap.put( repository.getId().trim(), repository );
        }

        for ( Repository repository : modifiedRepositories )
        {
            modifiedRepositoryMap.put( repository.getId().trim(), repository );
        }

        for ( Map.Entry<String, Repository> entry : modifiedRepositoryMap.entrySet() )
        {
            Repository repo1 = entry.getValue();
            Repository repo2 = originalRepositoryMap.get( entry.getKey() );

            if ( hasRepositoryPolicyDiff( repo1.getSnapshots(), repo2.getSnapshots() )
                || hasRepositoryPolicyDiff( repo1.getReleases(), repo2.getReleases() ) )
            {
                list.add( repo1 );
            }
        }

        return list;
    }

    private boolean hasRepositoryPolicyDiff( RepositoryPolicy p1, RepositoryPolicy p2 )
    {
        if ( !( p1 == null && p2 == null )
            && ( p1 == null || p2 == null || p1.isEnabled() != p2.isEnabled()
                || !isStringEqual( p1.getChecksumPolicy(), p2.getChecksumPolicy() ) || !isStringEqual(
                                                                                                       p1.getUpdatePolicy(),
                                                                                                       p2.getUpdatePolicy() ) ) )
        {
            return true;
        }

        return false;
    }

    private boolean hasProfilePropertiesDiff( Properties prop1, Properties prop2 )
    {
        if ( !( prop1 == null && prop2 == null ) && ( prop1 == null || prop2 == null ) )
        {
            return true;
        }

        for ( Map.Entry<Object, Object> entry : prop1.entrySet() )
        {
            String key1 = (String) entry.getKey();
            String value1 = (String) entry.getValue();

            String value2 = prop1.getProperty( key1 );
            if ( value2 == null || !value2.trim().equals( value1 ) )
            {
                return true;
            }
        }
        return false;
    }

    private boolean hasActivationDiff( Activation activation1, Activation activation2 )
    {
        if ( !( activation1 == null && activation2 == null )
            && ( activation1 == null || activation2 == null
                || activation1.isActiveByDefault() != activation2.isActiveByDefault()
                || !isStringEqual( activation1.getJdk(), activation2.getJdk() )
                || hasActivationOsDiff( activation1.getOs(), activation2.getOs() )
                || hasActivationFileDiff( activation1.getFile(), activation2.getFile() ) || hasActivationPropertyDiff(
                                                                                                                       activation1.getProperty(),
                                                                                                                       activation2.getProperty() ) ) )
        {
            return true;
        }

        return false;
    }

    private boolean hasActivationPropertyDiff( ActivationProperty prop1, ActivationProperty prop2 )
    {
        if ( !( prop1 == null && prop2 == null )
            && ( prop1 == null || prop2 == null || !isStringEqual( prop1.getName(), prop2.getName() ) || !isStringEqual(
                                                                                                                         prop1.getValue(),
                                                                                                                         prop2.getValue() ) ) )
        {
            return true;
        }
        return false;
    }

    private boolean hasActivationFileDiff( ActivationFile file1, ActivationFile file2 )
    {
        if ( !( file1 == null && file2 == null )
            && ( file1 == null || file2 == null || !isStringEqual( file1.getExists(), file2.getExists() ) || !isStringEqual(
                                                                                                                             file1.getMissing(),
                                                                                                                             file2.getMissing() ) ) )
        {
            return true;
        }
        return false;
    }

    private boolean hasActivationOsDiff( ActivationOS os1, ActivationOS os2 )
    {
        if ( !( os1 == null && os2 == null )
            && ( os1 == null || os2 == null || !isStringEqual( os1.getArch(), os2.getArch() )
                || !isStringEqual( os1.getFamily(), os2.getFamily() ) || !isStringEqual( os1.getName(), os2.getName() ) || !isStringEqual(
                                                                                                                                           os1.getVersion(),
                                                                                                                                           os2.getVersion() ) ) )
        {
            return true;
        }
        return false;
    }

    private boolean isStringEqual( String str1, String str2 )
    {
        return isNullorWhiteSpace( str1 ) && isNullorWhiteSpace( str2 ) ? true : isNullorWhiteSpace( str1 )
            && !isNullorWhiteSpace( str2 ) ? false : !isNullorWhiteSpace( str1 ) && isNullorWhiteSpace( str2 ) ? false
                        : str1.trim().equals( str2.trim() );
    }

    private boolean isNullorWhiteSpace( String str )
    {
        return str == null || str.trim().equals( "" );
    }
}
