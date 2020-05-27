package com.company.enroller.persistence;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("meetingService")
public class MeetingService {

    DatabaseConnector connector;

    public MeetingService() {
        connector = DatabaseConnector.getInstance();
    }

    public Collection<Meeting> getAll() {
        String hql = "FROM Meeting";
        Query query = connector.getSession().createQuery(hql);
        return query.list();
    }


    public Meeting findByIdMeeting(Long id) {
        return (Meeting) connector.getSession().get(Meeting.class, id);
    }


    public Meeting add(Meeting meeting) {
        Transaction transaction = this.connector.getSession().beginTransaction();
        connector.getSession().save(meeting);
        transaction.commit();

        return meeting;
    }

    public Collection<Participant> getEnrolled(Long id) {
        Meeting meeting = findByIdMeeting(id);
        return meeting.getParticipants();
     }

    public Meeting enrollParticipantToMeeting(long id, Participant participant) {
        Transaction transaction = this.connector.getSession().beginTransaction();
        Meeting meeting = findByIdMeeting(id);
        meeting.addParticipant(participant);
        connector.getSession().merge(meeting);
        transaction.commit();

        return meeting;
    }

    public void delete(Meeting meeting) {
        Transaction transaction = this.connector.getSession().beginTransaction();
        connector.getSession().delete(meeting);
        transaction.commit();
    }

    public void update(Meeting foundMeeting) {
        Transaction transaction = this.connector.getSession().beginTransaction();
        connector.getSession().update(foundMeeting);
        transaction.commit();
    }

    public void deleteParticipantToMeeting(long id, Participant participant) {
        Transaction transaction = this.connector.getSession().beginTransaction();
        Meeting meeting = findByIdMeeting(id);
        meeting.removeParticipant(participant);
        connector.getSession().merge(meeting);
        transaction.commit();
    }
}
