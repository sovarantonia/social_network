package repository.memory;

import domain.Request;
import repository.Repository;
import validator.RequestValidator;

import java.util.ArrayList;

public class MemoryRepoRequest implements Repository<Request, Long> {
    private final ArrayList<Request> requests;
    private final RequestValidator requestValidator;

    public MemoryRepoRequest(RequestValidator requestValidator) {
        this.requests = new ArrayList<>();
        this.requestValidator = requestValidator;
    }

    @Override
    public void add(Request e) {
        if(e == null){
            throw new IllegalArgumentException("Request cannot be null");
        }
        requestValidator.validate(e);
        requests.add(e);
    }

    @Override
    public void remove(Request e) {
        if(e == null){
            throw new IllegalArgumentException("Request cannot be null");
        }
        requestValidator.validate(e);
        int index = requests.indexOf(e);
        if (index == -1) {
            throw new IllegalArgumentException("Request does not exist");
        }
        requests.remove(index);
    }

    @Override
    public Request getObject(Long field) {
        for(Request r : requests){
            if(r.getId().equals(field)){
                return r;
            }
        }
        return null;
    }


    @Override
    public ArrayList<Request> getAll() {
        return requests;
    }
}
