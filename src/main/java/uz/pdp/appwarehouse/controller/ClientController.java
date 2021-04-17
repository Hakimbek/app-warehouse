package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Client;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    ClientService clientService;

    // ADD
    @PostMapping
    public Result add(@RequestBody Client client) {
        return clientService.add(client);
    }

    // GET
    @GetMapping
    public List<Client> get() {
        return clientService.get();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return clientService.getById(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return clientService.delete(id);
    }

    // EDIT
    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody Client client) {
        return clientService.edit(id, client);
    }
}
