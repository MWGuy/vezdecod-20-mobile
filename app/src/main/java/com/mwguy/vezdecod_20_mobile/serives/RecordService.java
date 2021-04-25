package com.mwguy.vezdecod_20_mobile.serives;

import android.content.Context;
import androidx.annotation.Nullable;
import com.mwguy.vezdecod_20_mobile.model.Category;
import com.mwguy.vezdecod_20_mobile.model.Record;
import com.mwguy.vezdecod_20_mobile.repositories.RecordRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecordService {
    private final RecordRepository recordRepository;

    public RecordService(Context context) {
        this.recordRepository = new RecordRepository(context);
    }

    public List<Record> getAllRecords() {
        List<Record> records = new ArrayList<>();
        for (String id : recordRepository.getAllIds()) {
            records.add(getById(id));
        }

        Collections.sort(records, (o1, o2) -> (int) (o2.getTimestamp() - o1.getTimestamp()));
        return records;
    }

    public List<Record> getByCategory(Category category) {
        List<Record> records = getAllRecords();
        List<Record> result = new ArrayList<>();

        for (Record record : records) {
            if (record.getCategoryId().equals(category.getId())) {
                result.add(record);
            }
        }

        return result;
    }

    public List<Record> search(String searchString, @Nullable Category category) {
        List<Record> records = category == null ? getAllRecords() : getByCategory(category);
        List<Record> result = new ArrayList<>();

        for (Record record : records) {
            if (record.getTitle().contains(searchString)) {
                result.add(record);
            }
        }

        return result;
    }

    public Record getById(String id) {
        return recordRepository.getRecordById(id);
    }

    public Record save(Record record) {
        return recordRepository.save(record);
    }

    public void remove(Record record) {
        recordRepository.remove(record);
    }
}
