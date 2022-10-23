package CRUD.service;

import CRUD.domain.Anime;
import CRUD.domain.Producer;
import CRUD.repository.AnimeRepository;
import CRUD.repository.ProducerRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AnimeService {
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
        List<Anime> animes = AnimeRepository.findByName(name);
        for(Anime allAnimes: animes){
            System.out.printf("Id : %d | Name:  %s | Episodes : %d | Producer_id : %d  %n",allAnimes.getId(),allAnimes.getName(),allAnimes.getEpisodes(),allAnimes.getProducer_id());
        }
    }


    private static void delete() {
        System.out.println("Type the id of anime you want to delete");
        String a = SCANNER.nextLine();
        if (!a.isEmpty()) {
            int id = Integer.parseInt(a);
            Optional<Anime> anime = AnimeRepository.findById(id);
            if (anime.isEmpty()) {
                System.out.println("Anime not exist");
                return;
            } else {
                System.out.println("Sure ? S/N");
                String choice = SCANNER.nextLine();
                if ("s".equalsIgnoreCase(choice)) {
                    AnimeRepository.delete(id);
                    return;
                }
            }

        }
        System.out.println("The id can't be is null");
    }

    private static void save(){
        System.out.println("Type the name of animes you want to insert in database,can't be null");
        String name = SCANNER.nextLine();
        System.out.println("Type the episodes of animes you want to insert in database,can't be null");
        int episodes = Integer.parseInt(SCANNER.nextLine());
        System.out.println("Type the producer_id of animes you want to insert in database");
        int producer_id = Integer.parseInt(SCANNER.nextLine());
        Optional<Producer> producerOptional = ProducerRepository.findById(producer_id);
        if(producerOptional.isEmpty()){
            System.out.println("Producer_id not found");
            return;
        }
        if(name.isEmpty()){
            System.out.println("The Name can't be is null");
        }else{
            Anime anime = Anime.builder().name(name).episodes(episodes).producer_id(producer_id).build();
            AnimeRepository.save(anime);
        }
    }

    private static void update(){
        System.out.println("Type the name of Anime you want to update in database");
        String name = SCANNER.nextLine();
        List<Anime> anime = AnimeRepository.findByName(name);
        System.out.println("New name you want update in database");
        String nameNew = SCANNER.nextLine();
        if(!anime.isEmpty() && !nameNew.isEmpty()){
            System.out.println("New episodes you want update in database");
            int episodesNew = Integer.parseInt(SCANNER.nextLine());
            System.out.println("New producer_id you want update in database");
            int producer_idNew = Integer.parseInt(SCANNER.nextLine());
            Anime newAnime = Anime.builder().id(anime.get(0).getId()).name(nameNew).episodes(episodesNew).producer_id(producer_idNew).build();
            AnimeRepository.update(newAnime);
        }else{
            System.out.println("Error trying update in database:Anime not exist in database or new name is null");
        }

    }
}
