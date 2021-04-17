package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Client;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    // ADD
    public Result add(Client client) {
        boolean existsByPhoneNumber = clientRepository.existsByPhoneNumber(client.getPhoneNumber());
        if (existsByPhoneNumber) {
            return new Result("Client already exist", false);
        }

        Client addClient = new Client();
        addClient.setName(client.getName());
        addClient.setPhoneNumber(client.getPhoneNumber());
        clientRepository.save(addClient);
        return new Result("Successfully added", true);
    }

    // GET
    public List<Client> get() {
        return clientRepository.findAll();
    }

    // GET BY ID
    public Result getById(Integer id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (!optionalClient.isPresent()) {
            return new Result("Client not found", false);
        }

        return new Result("Success", true, optionalClient.get());
    }

    // DELETE
    public  Result delete(Integer id) {
        try {
            clientRepository.deleteById(id);
            return new Result("Successfully deleted", true);
        } catch (Exception e) {
            return new Result("Error", false);
        }
    }

    // EDIT
    public Result edit(Integer id, Client client) {
        boolean existsByPhoneNumber = clientRepository.existsByPhoneNumber(client.getPhoneNumber());
        if (existsByPhoneNumber) {
            return new Result("Client already exist", false);
        }

        Optional<Client> optionalClient = clientRepository.findById(id);
        if (!optionalClient.isPresent()) {
            return new Result("Client not found", false);
        }

        Client editedClient = optionalClient.get();
        editedClient.setName(client.getName());
        editedClient.setPhoneNumber(client.getPhoneNumber());
        editedClient.setActive(client.isActive());
        clientRepository.save(editedClient);
        return new Result("Successfully edited", true);
    }
}
