package projectPS.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import projectPS.dto.food.FoodDishResponseDTO;
import projectPS.dto.order.OrderResponseDTO;
import projectPS.dto.order.SummaryResponseDTO;
import projectPS.exception.ExceptionBody;
import projectPS.service.order.OrderService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "Gets all orders.", description = "Only employees can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Orders not found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> getAll(
            @RequestParam(value = "sortBy", required = false, defaultValue = "totalPaycheck") String sortBy,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize
    ) {
        return new ResponseEntity<>(
                orderService.getOrders(sortBy, pageNumber, pageSize),
                HttpStatus.OK);
    }

    @GetMapping("/{idClient}")
    @Operation(summary = "Gets orders for a client.", description = "Only clients and employees can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Order not found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasAnyRole('CLIENT', 'EMPLOYEE')")
    public ResponseEntity<List<OrderResponseDTO>> getByClientId(@PathVariable("idClient") UUID idClient) {
        return new ResponseEntity<>(
                orderService.getOrdersByClient(idClient),
                HttpStatus.OK);
    }

    @PostMapping("/new/{idClient}")
    @Operation(summary = "Creates a new order for a client.", description = "Only clients and employees can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Order submitted wrong.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "404", description = "Client not found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasAnyRole('CLIENT', 'EMPLOYEE')")
    public ResponseEntity<OrderResponseDTO> createOrderForClient(@PathVariable("idClient") UUID idClient) {
        return new ResponseEntity<>(
                orderService.saveOrder(idClient),
                HttpStatus.CREATED);
    }

    @PostMapping("/{idOrder}")
    @Operation(summary = "Adds a new food dish to an order.", description = "Only clients and employees can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Food dish added successfully.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Order submitted wrong.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "404", description = "Order or food dish not found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasAnyRole('CLIENT', 'EMPLOYEE')")
    public ResponseEntity<Void> addFoodDish(@PathVariable("idOrder") UUID idOrder, @RequestBody List<UUID> idsFoodDish) {
        orderService.saveFoodDishesToOrder(idOrder, idsFoodDish);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{idOrder}/{idFoodDish}")
    @Operation(summary = "Deletes a food dish from an order.", description = "Only clients and employees can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Food dish deleted successfully.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Order or food dish not found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasAnyRole('CLIENT', 'EMPLOYEE')")
    public ResponseEntity<Void> deleteFoodDish(@PathVariable("idOrder") UUID idOrder, @PathVariable("idFoodDish") UUID idFoodDish) {
        orderService.deleteFoodDish(idOrder, idFoodDish);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{idOrder}")
    @Operation(summary = "Deletes an existing order.", description = "Only clients and employees can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order deleted successfully.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Order to be deleted not found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasAnyRole('CLIENT', 'EMPLOYEE')")
    public ResponseEntity<Void> deleteOrder(@PathVariable("idOrder") UUID idOrder) {
        orderService.deleteById(idOrder);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/summary")
    @Operation(summary = "Gets the summary of orders for a specified date.", description = "Only admins can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Summary generated successfully.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Not a valid date for summary.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SummaryResponseDTO> getSummary(@RequestParam LocalDate summaryDate) {
        return new ResponseEntity<>(
                orderService.getSummary(summaryDate),
                HttpStatus.OK);
    }
}
