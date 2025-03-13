# 스프링 부트 data.sql 에서 데이터베이스를 만들어줄 수 없기 때문에 이곳에서 데이터베이스를 만들어준다.
create database IF NOT EXISTS project_db CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

# 주의) 부트의 data.sql 에서 불가능한 작업만 작성하고 실행시키도록 한다.
# 참고) h2 in-memory db 도 지원하기 위해 data.sql 에서 사용자 기본 데이터를 삽입하도록 하고 있음.