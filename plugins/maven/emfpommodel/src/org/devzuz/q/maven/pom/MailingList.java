/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.devzuz.q.maven.pom;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mailing List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 3.0.0+
 * 
 *         This element describes all of the mailing lists associated with
 *         a project. The auto-generated site references this information.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.devzuz.q.maven.pom.MailingList#getName <em>Name</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.MailingList#getSubscribe <em>Subscribe</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.MailingList#getUnsubscribe <em>Unsubscribe</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.MailingList#getPost <em>Post</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.MailingList#getArchive <em>Archive</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.MailingList#getOtherArchives <em>Other Archives</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.devzuz.q.maven.pom.PomPackage#getMailingList()
 * @model extendedMetaData="name='MailingList' kind='elementOnly'"
 * @generated
 */
public interface MailingList extends EObject
{
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 3.0.0+
     * The name of the mailing list.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getMailingList_Name()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='name' namespace='##targetNamespace'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.MailingList#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Subscribe</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 3.0.0+
     * 
     *             The email address or link that can be used to subscribe to the mailing list.
     *             If this is an email address, a
     *             &lt;code&gt;mailto:&lt;/code&gt; link will automatically be created when
     *             the documentation is created.
     *           
     * <!-- end-model-doc -->
     * @return the value of the '<em>Subscribe</em>' attribute.
     * @see #setSubscribe(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getMailingList_Subscribe()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='subscribe' namespace='##targetNamespace'"
     * @generated
     */
    String getSubscribe();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.MailingList#getSubscribe <em>Subscribe</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Subscribe</em>' attribute.
     * @see #getSubscribe()
     * @generated
     */
    void setSubscribe(String value);

    /**
     * Returns the value of the '<em><b>Unsubscribe</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 3.0.0+
     * 
     *             The email address or link that can be used to unsubscribe to
     *             the mailing list.  If this is an email address, a
     *             &lt;code&gt;mailto:&lt;/code&gt; link will automatically be created
     *             when the documentation is created.
     *           
     * <!-- end-model-doc -->
     * @return the value of the '<em>Unsubscribe</em>' attribute.
     * @see #setUnsubscribe(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getMailingList_Unsubscribe()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='unsubscribe' namespace='##targetNamespace'"
     * @generated
     */
    String getUnsubscribe();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.MailingList#getUnsubscribe <em>Unsubscribe</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Unsubscribe</em>' attribute.
     * @see #getUnsubscribe()
     * @generated
     */
    void setUnsubscribe(String value);

    /**
     * Returns the value of the '<em><b>Post</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 3.0.0+
     * 
     *             The email address or link that can be used to post to
     *             the mailing list.  If this is an email address, a
     *             &lt;code&gt;mailto:&lt;/code&gt; link will automatically be created
     *             when the documentation is created.
     *           
     * <!-- end-model-doc -->
     * @return the value of the '<em>Post</em>' attribute.
     * @see #setPost(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getMailingList_Post()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='post' namespace='##targetNamespace'"
     * @generated
     */
    String getPost();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.MailingList#getPost <em>Post</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Post</em>' attribute.
     * @see #getPost()
     * @generated
     */
    void setPost(String value);

    /**
     * Returns the value of the '<em><b>Archive</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 3.0.0+
     * 
     *             The link to a URL where you can browse the mailing list archive.
     *           
     * <!-- end-model-doc -->
     * @return the value of the '<em>Archive</em>' attribute.
     * @see #setArchive(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getMailingList_Archive()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='archive' namespace='##targetNamespace'"
     * @generated
     */
    String getArchive();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.MailingList#getArchive <em>Archive</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Archive</em>' attribute.
     * @see #getArchive()
     * @generated
     */
    void setArchive(String value);

    /**
     * Returns the value of the '<em><b>Other Archives</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 3.0.0+
     * 
     *             The link to alternate URLs where you can browse the list archive.
     *           
     * <!-- end-model-doc -->
     * @return the value of the '<em>Other Archives</em>' containment reference.
     * @see #isSetOtherArchives()
     * @see #unsetOtherArchives()
     * @see #setOtherArchives(OtherArchivesType)
     * @see org.devzuz.q.maven.pom.PomPackage#getMailingList_OtherArchives()
     * @model containment="true" unsettable="true"
     *        extendedMetaData="kind='element' name='otherArchives' namespace='##targetNamespace'"
     * @generated
     */
    OtherArchivesType getOtherArchives();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.MailingList#getOtherArchives <em>Other Archives</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Other Archives</em>' containment reference.
     * @see #isSetOtherArchives()
     * @see #unsetOtherArchives()
     * @see #getOtherArchives()
     * @generated
     */
    void setOtherArchives(OtherArchivesType value);

    /**
     * Unsets the value of the '{@link org.devzuz.q.maven.pom.MailingList#getOtherArchives <em>Other Archives</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetOtherArchives()
     * @see #getOtherArchives()
     * @see #setOtherArchives(OtherArchivesType)
     * @generated
     */
    void unsetOtherArchives();

    /**
     * Returns whether the value of the '{@link org.devzuz.q.maven.pom.MailingList#getOtherArchives <em>Other Archives</em>}' containment reference is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Other Archives</em>' containment reference is set.
     * @see #unsetOtherArchives()
     * @see #getOtherArchives()
     * @see #setOtherArchives(OtherArchivesType)
     * @generated
     */
    boolean isSetOtherArchives();

} // MailingList
