package projectPS.service.order;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import projectPS.dto.CollectionResponseDTO;
import projectPS.dto.order.OrderResponseDTO;
import projectPS.dto.order.SummaryResponseDTO;
import projectPS.exception.ExceptionCode;
import projectPS.exception.NotFoundException;
import projectPS.mapper.OrderMapper;
import projectPS.model.food.FoodDishEntity;
import projectPS.model.order.OrderEntity;
import projectPS.model.person.client.ClientEntity;
import projectPS.repository.ClientRepository;
import projectPS.repository.FoodDishRepository;
import projectPS.repository.OrderRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Service implementation class that provides operations related to orders.
 */
@Slf4j
@RequiredArgsConstructor
public class OrderServiceBean implements OrderService {
    private final OrderRepository orderRepository;
    private final FoodDishRepository foodDishRepository;
    private final ClientRepository clientRepository;
    private final OrderMapper orderMapper;
    private final String applicationName;

    @Override
    public CollectionResponseDTO<OrderResponseDTO> getOrders(String sortBy, Integer pageNumber, Integer pageSize) {
        log.info("Getting all orders for application {}", applicationName);
        Page<OrderEntity> orderEntityPage = orderRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)));
        List<OrderResponseDTO> orders = orderMapper.orderEntityListTOOrderResponseDTOList(orderEntityPage.getContent());
        return new CollectionResponseDTO<>(orders, orderEntityPage.getTotalElements());
    }

    @Override
    public List<OrderResponseDTO> getOrdersByClient(UUID idClient) {
        if (!clientRepository.existsById(idClient)) {
            throw new NotFoundException(String.format(
                    ExceptionCode.ERR003_CLIENT_NOT_FOUND.getMessage(),
                    idClient
            ));
        }
        List<OrderEntity> orders = orderRepository.findByClientId(idClient);
        if (orders.isEmpty()) {
            throw new NotFoundException(String.format(
                    ExceptionCode.ERR005_ORDER_FOR_CLIENT_NOT_FOUND.getMessage(),
                    idClient
            ));
        }
        return orderMapper.orderEntityListTOOrderResponseDTOList(orders);
    }

    @Override
    @Transactional
    public OrderResponseDTO saveOrder(UUID idClient) {
        ClientEntity clientForOrder = clientRepository.findById(idClient)
                .orElseThrow(() -> new NotFoundException(String.format(
                        ExceptionCode.ERR003_CLIENT_NOT_FOUND.getMessage(),
                        idClient
                )));
        OrderEntity orderToBeAdded = new OrderEntity();
        orderToBeAdded.setClient(clientForOrder);
        orderToBeAdded.setOrderDate(LocalDate.now());
        OrderEntity orderAdded = orderRepository.save(orderToBeAdded);
        return orderMapper.orderEntityToOrderResponseDTO(orderAdded);
    }

    @Override
    @Transactional
    public void saveFoodDishesToOrder(UUID idOrder, List<UUID> idsFoodDish) {
        OrderEntity order = orderRepository.findById(idOrder)
                .orElseThrow(() -> new NotFoundException(String.format(
                        ExceptionCode.ERR005_ORDER_FOR_CLIENT_NOT_FOUND.getMessage(),
                        idOrder
                )));
        idsFoodDish.forEach(idFoodDish -> {
            Double foodDishPrice = foodDishRepository.findById(idFoodDish)
                    .map(FoodDishEntity::getPrice)
                    .orElseThrow(() -> new NotFoundException(String.format(
                            ExceptionCode.ERR001_FOOD_DISH_NOT_FOUND.getMessage(),
                            idFoodDish
                    )));

            order.setTotalPaycheck(order.getTotalPaycheck() + foodDishPrice);
            orderRepository.save(order);
            orderRepository.saveFoodDish(order.getId(), idFoodDish);
        });
    }

    @Override
    public SummaryResponseDTO getSummary(LocalDate summaryDate) {
        List<OrderEntity> orders = orderRepository.findByOrderDateIs(summaryDate);
        Double totalIncome = orders.stream().map(OrderEntity::getTotalPaycheck).reduce(0.0, Double::sum);
        return new SummaryResponseDTO(orderMapper.orderEntityListTOOrderResponseDTOList(orders), totalIncome);
    }

    @Override
    @Transactional
    public void deleteById(UUID idOrder) {
        OrderEntity orderToBeDeleted = orderRepository.findById(idOrder)
                .orElseThrow(() -> new NotFoundException(String.format(
                        ExceptionCode.ERR004_ORDER_NOT_FOUND.getMessage(),
                        idOrder
                )));
        orderRepository.deleteById(orderToBeDeleted.getId());
    }

    @Override
    @Transactional
    public void deleteFoodDish(UUID idOrder, UUID idFoodDish) {
        Double foodDishPrice = foodDishRepository.findById(idFoodDish)
                .map(FoodDishEntity::getPrice)
                .orElseThrow(() -> new NotFoundException(String.format(
                        ExceptionCode.ERR001_FOOD_DISH_NOT_FOUND.getMessage(),
                        idFoodDish
                )));
        OrderEntity order = orderRepository.findById(idOrder)
                .orElseThrow(() -> new NotFoundException(String.format(
                        ExceptionCode.ERR005_ORDER_FOR_CLIENT_NOT_FOUND.getMessage(),
                        idOrder
                )));
        order.setTotalPaycheck(order.getTotalPaycheck() - foodDishPrice);
        orderRepository.deleteFoodDish(order.getId(), idFoodDish);
    }
}
