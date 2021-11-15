package zw.org.zvandiri.report.api.service.parallel;

import zw.org.zvandiri.business.domain.Contact;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.report.api.GenericReportModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ContactReportTask extends RecursiveTask<List> {

    List<Contact> contacts;
    public ContactReportTask(List<Contact> items ){
        this.contacts=items;
    }


    @Override
    protected List compute() {
        if (contacts.size() <= 1000) {
            return process();
        } else {
            int mid = contacts.size() / 2;

            ContactReportTask first = new ContactReportTask(contacts.subList(0, mid));
            ContactReportTask second = new ContactReportTask(contacts.subList(mid, contacts.size()));
            first.fork();
            List list = second.compute();
            list.addAll(first.join());



            return list;
        }
    }

    public List<GenericReportModel> process(){
        List<GenericReportModel> items = new ArrayList<>();
        for (Contact item : contacts) {
            String[] inner = {
                    item.getPatient().getName(),
                    item.getPatient().getAge() + "",
                    item.getPatient().getGender().getName(),
                    item.getPatient().getMobileNumber(),
                    item.getPatient().getCat()!=null?item.getPatient().getCat().getName():"",
                    item.getPatient().getYoungMumGroup()!=null?item.getPatient().getYoungMumGroup().getName():"",
                    item.getPatient().getYoungDadGroup()!=null?item.getPatient().getYoungDadGroup().getName():"",
                    item.getPatient().getPrimaryClinic().getDistrict().getName(),
                    item.getPatient().getPrimaryClinic().getName(),
                    item.getDateCreated()!=null ? item.getDateCreated().toString() : "",
                    item.getCareLevel().getName(),
                    DateUtil.getStringFromDate(item.getContactDate()),
                    item.getFollowUp().getName(),
                    item.getLocation().getName(),
                    item.getCareLevel().getName(),
                    item.getPosition().getName()
            };
            items.add(new GenericReportModel(Arrays.asList(inner)));
        }
        return items;
    }
}
