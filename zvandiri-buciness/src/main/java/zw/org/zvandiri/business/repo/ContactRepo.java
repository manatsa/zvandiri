/*
 * Copyright 2016 Judge Muzinda.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package zw.org.zvandiri.business.repo;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zw.org.zvandiri.business.domain.Contact;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.User;

/**
 *
 * @author Judge Muzinda
 */
public interface ContactRepo extends AbstractRepo<Contact, String> {
 
    @Query("from Contact c left join fetch c.createdBy left join fetch c.modifiedBy left join fetch c.patient left join fetch c.location left join fetch c.position left join fetch c.internalReferral left join fetch c.externalReferral left join fetch c.stables left join fetch c.enhanceds left join fetch c.parent left join fetch c.assessments where c.id=:id")
    public Contact findById(@Param("id") String id);
    
    @Query("Select Distinct(c) from Contact c where c.patient=:patient")
    public List<Contact> findByPatient(@Param("patient") Patient patient);
    
    @Query("Select Distinct(c) from Contact c where c.patient=:patient and (c.contactDate) between :start and :end")
    public List<Contact> findByPatientAndContactDate(
            @Param("patient") Patient patient,
            @Param("start") Date start, @Param("end") Date end);
    
    @Query("Select Distinct(c) from Contact c left join fetch c.patient left join fetch c.location left join fetch c.position left join fetch c.period left join fetch c.internalReferral left join fetch c.externalReferral left join fetch c.assessments left join fetch c.actionTaken left join fetch c.stables left join fetch c.enhanceds left join fetch c.parent left join fetch c.referredPerson")
    public List<Contact> findByAllContacts();
    
    @Query("Select Distinct(c) from Contact c left join fetch c.createdBy left join fetch c.patient left join fetch c.location left join fetch c.position left join fetch c.period left join fetch c.internalReferral left join fetch c.externalReferral left join fetch c.assessments left join fetch c.actionTaken left join fetch c.stables left join fetch c.enhanceds left join fetch c.parent left join fetch c.referredPerson where c.referredPerson=:referredPerson and c.open=:open order by c.contactDate DESC")
    public List<Contact> findByReferredPersonAndOpenOrderByContactDateDesc(@Param("referredPerson") User referredPerson, @Param("open") Boolean open);
}