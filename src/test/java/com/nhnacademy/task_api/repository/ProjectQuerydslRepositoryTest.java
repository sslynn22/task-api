//package com.nhnacademy.task_api.repository;
//
//import com.nhnacademy.task_api.domain.model.Member;
//import com.nhnacademy.task_api.domain.model.Project;
//import com.nhnacademy.task_api.domain.model.Status;
//import com.nhnacademy.task_api.repository.Impl.ProjectQuerydslRepositoryImpl;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//public class ProjectQuerydslRepositoryTest {
//    @Autowired
//    @Qualifier("projectQuerydslRepositoryImpl")
//    private ProjectQuerydslRepositoryImpl projectQuerydslRepository;
//    @Autowired
//    private ProjectRepository projectRepository;
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Test
//    @DisplayName("find project : member table")
//    void findProject_memberTable_test() {
//        Project project1 = new Project("Project A", "user1", Status.ACTIVE);
//        Project project2 = new Project("Project B", "user2", Status.ACTIVE);
//        projectRepository.save(project1);
//        projectRepository.save(project2);
//
//        memberRepository.save(new Member("user1", project2));
//
//        List<Project> findProjects = projectQuerydslRepository.findAllByProject_memberId("user1");
//        assertThat(findProjects).hasSize(1);
//        assertThat(findProjects.getFirst()).isEqualTo(project1);
//    }
//}
