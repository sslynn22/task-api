CREATE TABLE `tag` (
                       `tag_id`	bigint	NOT NULL,
                       `project_id`	bigint	NOT NULL,
                       `tag_name`	varchar(50)	NOT NULL
);

CREATE TABLE `task` (
	`task_id`	bigint	NOT NULL,
	`project_id`	bigint	NOT NULL,
	`milestone_id`	bigint	NULL,
	`task_name`	varchar(50)	NOT NULL,
	`user_id`	varchar(50)	NOT NULL,
	`manager_id`	varchar(50)	NULL,
	`created_at`	datetime	NOT NULL
);

CREATE TABLE `comment` (
	`comment_id`	bigint	NOT NULL,
	`task_id`	bigint	NOT NULL,
	`writer_id`	varchar(50)	NOT NULL,
	`created_at`	datetime	NOT NULL,
	`content`	text	NOT NULL
);

CREATE TABLE `tasktag` (
	`task_id`	bigint	NOT NULL,
	`tag_id`	bigint	NOT NULL
);

CREATE TABLE `project` (
	`project_id`	bigint	NOT NULL,
	`project_name`	varachar(30)	NOT NULL,
	`create_at`	datetime	NOT NULL,
	`admin_id`	varchar(50)	NOT NULL,
	`proeject_status`	ENUM('ACTIVE', 'DORMANT', 'COMPLETED')	NOT NULL
);

CREATE TABLE `member` (
	`member_id`	varchar(50)	NOT NULL,
	`project_id`	bigint	NOT NULL
);

CREATE TABLE `milestone` (
	`milestone_id`	bigint	NOT NULL,
	`project_id`	bigint	NOT NULL,
	`milestone_name`	varchar(50)	NOT NULL,
	`start_date`	date	NULL,
	`end_date`	date	NULL
);

ALTER TABLE `tag` ADD CONSTRAINT `PK_TAG` PRIMARY KEY (
	`tag_id`
);

ALTER TABLE `task` ADD CONSTRAINT `PK_TASK` PRIMARY KEY (
	`task_id`
);

ALTER TABLE `comment` ADD CONSTRAINT `PK_COMMENT` PRIMARY KEY (
	`comment_id`
);

ALTER TABLE `tasktag` ADD CONSTRAINT `PK_TASKTAG` PRIMARY KEY (
	`task_id`,
	`tag_id`
);

ALTER TABLE `project` ADD CONSTRAINT `PK_PROJECT` PRIMARY KEY (
	`project_id`
);

ALTER TABLE `member` ADD CONSTRAINT `PK_MEMBER` PRIMARY KEY (
	`member_id`,
	`project_id`
);

ALTER TABLE `milestone` ADD CONSTRAINT `PK_MILESTONE` PRIMARY KEY (
	`milestone_id`
);

ALTER TABLE `tag` ADD CONSTRAINT `FK_project_TO_tag_1` FOREIGN KEY (
	`project_id`
)
REFERENCES `project` (
	`project_id`
);

ALTER TABLE `task` ADD CONSTRAINT `FK_project_TO_task_1` FOREIGN KEY (
	`project_id`
)
REFERENCES `project` (
	`project_id`
);

ALTER TABLE `task` ADD CONSTRAINT `FK_milestone_TO_task_1` FOREIGN KEY (
	`milestone_id`
)
REFERENCES `milestone` (
	`milestone_id`
);

ALTER TABLE `comment` ADD CONSTRAINT `FK_task_TO_comment_1` FOREIGN KEY (
	`task_id`
)
REFERENCES `task` (
	`task_id`
);

ALTER TABLE `tasktag` ADD CONSTRAINT `FK_task_TO_tasktag_1` FOREIGN KEY (
	`task_id`
)
REFERENCES `task` (
	`task_id`
);

ALTER TABLE `tasktag` ADD CONSTRAINT `FK_tag_TO_tasktag_1` FOREIGN KEY (
	`tag_id`
)
REFERENCES `tag` (
	`tag_id`
);

ALTER TABLE `member` ADD CONSTRAINT `FK_project_TO_member_1` FOREIGN KEY (
	`project_id`
)
REFERENCES `project` (
	`project_id`
);

ALTER TABLE `milestone` ADD CONSTRAINT `FK_project_TO_milestone_1` FOREIGN KEY (
	`project_id`
)
REFERENCES `project` (
	`project_id`
);

