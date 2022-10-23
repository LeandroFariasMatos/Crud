package CRUD.service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import CRUD.domain.Producer;
import CRUD.repository.ProducerRepository;

public class ProducerService {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void menu(int op){
        switch(op){
            case 1:
                findByName();
                break;
            case 2:
                delete();
                break;
            case 3:
                save();
                break;
            case 4:
                update();
                break;
            default:
                throw new IllegalArgumentException("Not a valid option");
        }
    }

    private static void findByName(){
        System.out.println("Type the name or empty to all");
        String name = SCANNER.nextLine();
        List<Producer> producers = ProducerRepository.findByName(name);
        for(Producer allProducers: producers){
            System.out.printf("Id : %d | Name: %s%n",allProducers.getId(),allProducers.getName());
        }
    }

    private static void delete() {
        System.out.println("Type the id of producer you want to delete");
        String a = SCANNER.nextLine();
        if (!a.isEmpty()) {
            int id = Integer.parseInt(a);
            Optional<Producer> producer = ProducerRepository.findById(id);
            if (producer.isEmpty()) {
                System.out.println("Producer not exist");
                return;
            } else {
                System.out.println("Sure ? S/N");
                String choice = SCANNER.nextLine();
                if ("s".equalsIgnoreCase(choice)) {
                    ProducerRepository.delete(id);
                    return;
                }
            }

        }
        System.out.println("The id can't be is null");
    }


    private static void save(){
        System.out.println("Type the name of producer you want to insert in database");
        String name = SCANNER.nextLine();
        if(name.isEmpty()){
            System.out.println("The Name can't be is null");
        }else{
            Producer producer = Producer.builder().name(name).build();
            ProducerRepository.save(producer);
        }
    }

    private static void update(){
        System.out.println("Type the name of producer you want to update in database");
        String name = SCANNER.nextLine();

        List<Producer> producer = ProducerRepository.findByName(name);
        System.out.println("New name you want update in database");
        String nameNew = SCANNER.nextLine();
        if(!producer.isEmpty() && !nameNew.isEmpty()){
            Producer novoProducer = Producer.builder().id(producer.get(0).getId()).name(nameNew).build();
            ProducerRepository.update(novoProducer);
        }else{
            System.out.println("Error trying update in database:Producer not exist in database or new name is null");
        }

    }


}
