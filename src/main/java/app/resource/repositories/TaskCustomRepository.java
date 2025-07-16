package app.resource.repositories;

import app.domain.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
public class TaskCustomRepository {

    @Autowired
    private  MongoOperations operations;

    public Page<Task> findPaginated(Task task, Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("priority").ascending());
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("priority", "state");

        Example<Task> example = Example.of(task, matcher);

        Query query = Query.query(Criteria.byExample(example)).with(pageable);

        if (task.getPriority() > 0){
            query.addCriteria(Criteria.where("priority").is(task.getPriority()));
        }

        if (task.getStatus() != null){
            query.addCriteria(Criteria.where("status").is(task.getStatus()));
        }

        return PageableExecutionUtils.getPage(
                operations.find(query, Task.class),
                pageable, () -> operations.count(query, Task.class)
        );
    }

}
