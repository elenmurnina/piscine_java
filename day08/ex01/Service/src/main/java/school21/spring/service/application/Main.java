package school21.spring.service.application;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

public class Main {
    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext context
                     = new ClassPathXmlApplicationContext("context.xml")) {

            System.out.println("== Test RepositoryJdbc ==");
            UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
            testUsersRepository(usersRepository);

            System.out.println("== Test RepositoryJdbcTemplate ==");
            UsersRepository usersRepositoryTemplate = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
            testUsersRepository(usersRepositoryTemplate);
        }
    }

    private static void testUsersRepository(UsersRepository usersRepository) {
        System.out.println("== Find All ==");
        for (User user : usersRepository.findAll()) {
            System.out.println(user);
        }
        System.out.println();

        System.out.println("== Find by ID ==");
        System.out.println(usersRepository.findById(2L));
        System.out.println(usersRepository.findById(4L));
        System.out.println(usersRepository.findById(42L));
        System.out.println();

        System.out.println("== Find by email ==");
        System.out.println(usersRepository.findByEmail("thirdUser@email.com"));
        System.out.println(usersRepository.findByEmail(usersRepository.findById(5L).getEmail()));
        System.out.println(usersRepository.findByEmail("unknownEmail"));
        System.out.println();

        System.out.println("== Save ==");
        User userSave = new User(null, "newUserEmail@lol.kek");
        usersRepository.save(userSave);
        System.out.println(usersRepository.findById(userSave.getId()));
        System.out.println();

        System.out.println("== Delete ==");
        Long id = userSave.getId();
        System.out.println(usersRepository.findById(id));
        usersRepository.delete(id);
        System.out.println(usersRepository.findById(id));
        System.out.println();

        System.out.println("== Update ==");
        System.out.println(usersRepository.findById(2L));
        User userUpdate = usersRepository.findById(2L);
        userUpdate.setEmail("updateNEW" + userUpdate.getEmail());
        usersRepository.update(userUpdate);
        System.out.println(usersRepository.findById(2L));
        System.out.println();
    }
}
