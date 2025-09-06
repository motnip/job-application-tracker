-- Test data for Application and ApplicationNote entities

-- Insert Applications
INSERT INTO applications (id, company_name, application_date, description, first_contact_date, state, creation_date, update_date) VALUES
(1, 'TechCorp Solutions', '2024-01-15', 'Senior Java Developer position focusing on Spring Boot and microservices architecture', '2024-01-18', 'IN_PROGRESS', '2024-01-15T10:30:00Z', NULL),
(2, 'InnovateSoft Ltd', '2024-01-20', 'Full Stack Developer role with React and Node.js', '2024-01-22', 'IN_PROGRESS', '2024-01-20T14:15:00Z', '2024-01-25T09:45:00Z'),
(3, 'DataDrive Analytics', '2024-02-01', 'Data Engineer position working with big data technologies', NULL, 'WAITING', '2024-02-01T16:20:00Z', NULL),
(4, 'CloudFirst Technologies', '2024-02-05', 'DevOps Engineer role focusing on AWS and Kubernetes', '2024-02-07', 'IN_PROGRESS', '2024-02-05T11:00:00Z', '2024-02-08T13:30:00Z'),
(5, 'StartupHub Inc', '2024-02-10', 'Lead Software Architect position in a growing fintech startup', '2024-02-12', 'REJECTED', '2024-02-10T09:15:00Z', '2024-02-15T16:45:00Z');

-- Insert ApplicationNotes
INSERT INTO application_notes (id, application_id, text, creation_time, update_time) VALUES
-- Notes for TechCorp Solutions (application_id = 1)
(1, 1, 'Applied through company website. HR mentioned they are looking for someone with 5+ years Spring Boot experience.', '2024-01-15T11:00:00Z', NULL),
(2, 1, 'Received automated confirmation email. They mentioned review process takes 2-3 weeks.', '2024-01-15T11:30:00Z', NULL),
(3, 1, 'HR called for initial screening. Positive conversation about technical background. Next step is technical interview.', '2024-01-18T15:45:00Z', NULL),

-- Notes for InnovateSoft Ltd (application_id = 2)
(4, 2, 'Found this position through LinkedIn. Salary range 70-85k, which matches my expectations.', '2024-01-20T14:30:00Z', NULL),
(5, 2, 'Had phone screening with hiring manager. They are impressed with my React portfolio projects.', '2024-01-22T10:15:00Z', NULL),
(6, 2, 'Technical interview scheduled for next week. Need to prepare algorithms and system design questions.', '2024-01-25T09:50:00Z', NULL),

-- Notes for DataDrive Analytics (application_id = 3)
(7, 3, 'Submitted application through recruiter. They specialize in data engineering roles.', '2024-02-01T16:30:00Z', NULL),
(8, 3, 'Recruiter mentioned the role involves working with Spark, Kafka, and Snowflake. Perfect match for my skills.', '2024-02-02T08:20:00Z', NULL),

-- Notes for CloudFirst Technologies (application_id = 4)
(9, 4, 'Direct application through company career page. They responded quickly with interest.', '2024-02-05T11:15:00Z', NULL),
(10, 4, 'Phone screening went well. They are looking for AWS certification which I have.', '2024-02-07T14:20:00Z', NULL),
(11, 4, 'Technical interview scheduled for Feb 12th. Need to review Kubernetes concepts and Terraform.', '2024-02-08T13:45:00Z', NULL),

-- Notes for StartupHub Inc (application_id = 5)
(12, 5, 'Applied after meeting founder at tech meetup. Very innovative company in fintech space.', '2024-02-10T09:30:00Z', NULL),
(13, 5, 'Had great initial conversation with CTO. They are building a new payment platform.', '2024-02-12T11:10:00Z', NULL),
(14, 5, 'Unfortunately, they decided to go with someone with more fintech domain experience.', '2024-02-15T16:50:00Z', NULL),
(15, 5, 'Received very positive feedback. They will keep my resume for future opportunities.', '2024-02-15T17:00:00Z', NULL);