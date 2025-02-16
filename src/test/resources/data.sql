CREATE TABLE employee
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(250),
    email        VARCHAR(250),
    job_title    VARCHAR(250),
    phone VARCHAR(250),
    address varchar(250),
    image_url VARCHAR(250),
    code_employee VARCHAR(250));


INSERT INTO employee(name,email,job_title,phone,address,image_url,code_employee)
VALUES ('daniel','daniel@gmail.com','testeur QA','691683541','yaounde','image1','AAAAA'),
       ('franck','franck@gmail.com','back-end','652365250','douala','image2','AABB'),
       ('daniel','daniel@gmail.com','front-end','620215793','bafoussam','image3','AACC');