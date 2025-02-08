package projectPS.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import projectPS.mapper.*;
import projectPS.repository.*;
import projectPS.service.food.FoodDishService;
import projectPS.service.food.FoodDishServiceBean;
import projectPS.service.mail.AsyncMailServiceBean;
import projectPS.service.mail.MailService;
import projectPS.service.order.OrderService;
import projectPS.service.order.OrderServiceBean;
import projectPS.service.person.PersonService;
import projectPS.service.person.PersonServiceBean;
import projectPS.service.person.client.ClientService;
import projectPS.service.person.client.ClientServiceBean;
import projectPS.service.person.employee.EmployeeService;
import projectPS.service.person.employee.EmployeeServiceBean;
import projectPS.service.reservation.ReservationService;
import projectPS.service.reservation.ReservationServiceBean;

/**
 * Configuration class for Spring Bean declarations.
 * This class defines Spring beans for various services used in the application.
 */
@Configuration
public class Config {

    /**
     * Bean declaration for FoodDishService.
     *
     * @param foodDishRepository The repository for food dishes.
     * @param foodDishMapper     The mapper for food dishes.
     * @param applicationName    The name of the application.
     * @return An instance of FoodDishServiceBean.
     */
    @Bean
    public FoodDishService foodDishServiceBean(
            FoodDishRepository foodDishRepository,
            FoodDishMapper foodDishMapper,
            @Value("${spring.application.name:BACKEND}") String applicationName
    ) {
        return new FoodDishServiceBean(foodDishRepository, foodDishMapper, applicationName);
    }

    /**
     * Bean declaration for ReservationService.
     *
     * @param reservationRepository The repository for reservations.
     * @param clientRepository      The repository for clients.
     * @param reservationMapper     The mapper for reservations.
     * @param applicationName       The name of the application.
     * @return An instance of ReservationServiceBean.
     */
    @Bean
    public ReservationService reservationServiceBean(
            ReservationRepository reservationRepository,
            ClientRepository clientRepository,
            ReservationMapper reservationMapper,
            @Value("projectPS") String applicationName
    ) {
        return new ReservationServiceBean(reservationRepository, clientRepository, reservationMapper, applicationName);
    }

    /**
     * Bean declaration for ClientService.
     *
     * @param clientRepository The repository for clients.
     * @param clientMapper     The mapper for clients.
     * @param applicationName  The name of the application.
     * @return An instance of ClientServiceBean.
     */
    @Bean
    public ClientService clientServiceBean(
            ClientRepository clientRepository,
            OrderRepository orderRepository,
            ClientMapper clientMapper,
            @Value("projectPS") String applicationName
    ) {
        return new ClientServiceBean(clientRepository, orderRepository, clientMapper, applicationName);
    }

    /**
     * Bean declaration for OrderService.
     *
     * @param orderRepository    The repository for orders.
     * @param foodDishRepository The repository for food dishes.
     * @param clientRepository   The repository for clients.
     * @param orderMapper        The mapper for orders.
     * @param applicationName    The name of the application.
     * @return An instance of OrderServiceBean.
     */
    @Bean
    public OrderService orderServiceBean(
            OrderRepository orderRepository,
            FoodDishRepository foodDishRepository,
            ClientRepository clientRepository,
            OrderMapper orderMapper,
            @Value("projectPS") String applicationName
    ) {
        return new OrderServiceBean(orderRepository, foodDishRepository, clientRepository, orderMapper, applicationName);
    }

    /**
     * Bean declaration for EmployeeService.
     *
     * @param employeeRepository The repository for employees.
     * @param employeeMapper     The mapper for employees.
     * @param applicationName    The name of the application.
     * @return An instance of EmployeeServiceBean.
     */
    @Bean
    public EmployeeService employeeServiceBean(
            EmployeeRepository employeeRepository,
            EmployeeMapper employeeMapper,
            @Value("projectPS") String applicationName
    ) {
        return new EmployeeServiceBean(employeeRepository, employeeMapper, applicationName);
    }

    @Bean
    public PersonService personServiceBean(
            PersonRepository personRepository,
            PersonMapper personMapper
    ) {
        return new PersonServiceBean(personRepository, personMapper);
    }

    @Bean
    public MailService asyncMailServiceBean(
            @Value("${queues.async-mail-sender-request}") String destination,
            JmsTemplate jmsTemplate,
            ObjectMapper objectMapper
    ) {
        return new AsyncMailServiceBean(destination, jmsTemplate, objectMapper);
    }
}
