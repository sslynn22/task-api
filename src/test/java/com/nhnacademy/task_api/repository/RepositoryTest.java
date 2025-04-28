package com.nhnacademy.task_api.repository;

import com.nhnacademy.task_api.domain.model.*;
import com.nhnacademy.task_api.domain.model.Tag;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RepositoryTest {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TaskTagRepository taskTagRepository;
    @Autowired
    private MileStoneRepository mileStoneRepository;

    private Project project1;
    private Project project2;
    private Project project3;

    @BeforeAll
    void setUp() {
        project1 = new Project("Project A", "user1", Status.ACTIVE);
        project2 = new Project("Project B", "user1", Status.DORMANT);
        project3 = new Project("Project C", "user2", Status.COMPLETED);

        projectRepository.save(project1);
        projectRepository.save(project2);
        projectRepository.save(project3);

        Task task1 = new Task("Task A", "user1", "user2", project1);
        Task task2 = new Task("Task B", "user1", "user3", project2);
        Task task3 = new Task("Task C", "user2", "user3");
        task3.setProject(project2);

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);

        Comment comment1 = new Comment("user1", "comment test1", task1);
        Comment comment2 = new Comment("user2", "comment test2", task2);
        Comment comment3 = new Comment("user3", "comment test3");
        comment3.setTask(task2);

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        Member member1 = new Member("user2", project1);
        Member member2 = new Member("user3", project1);
        Member member3 = new Member("user3", project2);

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        Tag tag1 = new Tag("tag A");
        tag1.setProject(project1);
        Tag tag2 = new Tag("tag B");
        tag2.setProject(project2);
        Tag tag3 = new Tag("tag C");
        tag3.setProject(project1);
        Tag tag4 = new Tag("tag D");
        tag4.setProject(project1);

        tagRepository.save(tag1);
        tagRepository.save(tag2);
        tagRepository.save(tag3);
        tagRepository.save(tag4);

        TaskTag taskTag1 = new TaskTag(task1, tag1);
        TaskTag taskTag2 = new TaskTag(task1, tag4);
        TaskTag taskTag3 = new TaskTag(task2, tag2);

        taskTagRepository.save(taskTag1);
        taskTagRepository.save(taskTag2);
        taskTagRepository.save(taskTag3);

        MileStone mileStone1 = new MileStone("milestone A", LocalDate.parse("2025-02-01"), LocalDate.parse("2025-02-10"));
        mileStone1.setProject(project1);
        MileStone mileStone2 = new MileStone("milestone B");
        mileStone2.setProject(project2);

        mileStoneRepository.save(mileStone1);
        mileStoneRepository.save(mileStone2);
    }

    @Test
    @DisplayName("ProjectRepository Test")
    void project_findAllByAdminId_test() {
        List<Project> projects = projectRepository.findAllByAdminId("user1");

        assertThat(projects).hasSize(2);
        assertThat(projects)
                .extracting(Project::getProjectName)
                .containsExactlyInAnyOrder("Project A", "Project B");
    }

    @Test
    @DisplayName("TaskRepository Test")
    void task_findAllByProject_ProjectId_test() {
        List<Task> tasks = taskRepository.findAllByProject_ProjectId(project2.getProjectId());

        assertThat(tasks).hasSize(2);
        assertThat(tasks)
                .extracting(Task::getProject)
                .extracting(Project::getProjectName)
                .containsExactlyInAnyOrder("Project B", "Project B");
    }

    @Test
    @DisplayName("CommentRepository Test")
    void comment_findAllByTask_TaskId_test() {
        List<Comment> comments = commentRepository.findAllByTask_TaskId(1L);

        assertThat(comments).hasSize(1);
        assertThat(comments)
                .extracting(Comment::getTask)
                .extracting(Task::getTaskName)
                .containsExactlyInAnyOrder("Task A");
    }

    @Test
    @DisplayName("MemberRepository Test")
    void member_findAllByProject_ProjectId_test() {
        List<Member> members = memberRepository.findAllByProject_ProjectId(project1.getProjectId());

        assertThat(members).hasSize(2);
        assertThat(members)
                .extracting(Member::getMemberPK)
                .extracting(MemberPk::getMemberId)
                .containsExactlyInAnyOrder("user2", "user3");
    }

    @Test
    @DisplayName("TagRepository Test")
    void tag_findAllByProject_ProjectId_test() {
        List<Tag> tags = tagRepository.findAllByProject_ProjectId(project1.getProjectId());

        assertThat(tags).hasSize(3);
        assertThat(tags)
                .extracting(Tag::getTagName)
                .containsExactlyInAnyOrder("tag A", "tag C", "tag D");
    }

    @Test
    @DisplayName("TaskTagRepository Test")
    void taskTag_findAllByTask_TaskId_test() {
        List<TaskTag> taskTags = taskTagRepository.findAllByTask_TaskId(1L);

        assertThat(taskTags).hasSize(2);
        assertThat(taskTags)
                .extracting(TaskTag::getTag)
                .extracting(Tag::getTagName)
                .containsExactlyInAnyOrder("tag A", "tag D");
    }

    @Test
    @DisplayName("MileStoneRepository Test")
    void mileStone_findAllByProject_ProjectId_test() {
        List<MileStone> mileStones = mileStoneRepository.findAllByProject_ProjectId(project2.getProjectId());

        assertThat(mileStones).hasSize(1);
        assertThat(mileStones)
                .extracting(MileStone::getMilestoneName)
                .containsExactlyInAnyOrder("milestone B");
    }
}
