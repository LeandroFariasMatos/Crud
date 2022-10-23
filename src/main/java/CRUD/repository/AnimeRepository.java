package CRUD.repository;

import CRUD.Connection.ConnectionFactory;
import CRUD.domain.Anime;
import CRUD.domain.Producer;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public class AnimeRepository {
    public static List<Anime> findByName(String name){
        log.info("Finding anime by name");
        List<Anime> animes = new ArrayList<>();
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = createPreparedStatementFindByName(conn,name);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()) {
                //Producer producer = Producer.builder().id(rs.getInt("producer_id")).name(rs.getString("producer_name")).build();
                Anime anime = Anime.builder().id(rs.getInt("id")).name(rs.getString("name")).episodes(rs.getInt("episodes")).producer_id(rs.getInt("producer_id")).build();
                animes.add(anime);
            }
        }catch (SQLException e){
            log.error("Error while trying to find all animes",e);
        }
        return animes;
    }

    private static PreparedStatement createPreparedStatementFindByName(Connection conn, String name) throws SQLException {
        String sql ="SELECT * FROM anime_store.anime where name like ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,"%"+name+"%");
        return ps;
    }

    public static void delete(int id){
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = createPreparedStatementDelete(conn,id)){
            ps.execute();
        }catch (SQLException e){
            log.error("Error while trying delete in anime by id");
        }

    }

    private static PreparedStatement createPreparedStatementDelete(Connection conn, Integer id) throws SQLException {
        String sql ="DELETE FROM `anime_store`.`anime` WHERE (`id` = ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,id);
        return ps;
    }

    public static void save(Anime anime){
        log.info("Trying Saving '{}' to database",anime);
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = createPreparedStatementSave(conn,anime)){
            ps.execute();
        }catch (SQLException e){
            log.error("Error while trying to saving anime '{}'",anime.getId(),e);
        }
    }

    private static PreparedStatement createPreparedStatementSave(Connection conn,Anime anime) throws SQLException{
        String sql = "INSERT INTO `anime_store`.`anime` (`name`,`episodes`,`producer_id`) VALUES (?, ? , ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,anime.getName());
        ps.setInt(2,anime.getEpisodes());
        ps.setInt(3,anime.getProducer_id());
        return ps;
    }

    public static void update(Anime anime){
        log.info("Trying update '{}' to database",anime);
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = createPreparedStatementUpdate(conn,anime)){
            ps.execute();
        }catch (SQLException e){
            log.info("Error while trying to update anime '{}' in database",anime);
        }

    }


    private static PreparedStatement createPreparedStatementUpdate(Connection conn,Anime anime) throws SQLException{
        String sql = "UPDATE `anime_store`.`anime` SET `name` = ?, `episodes` = ?, `producer_id` = ? WHERE (`id` = ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,anime.getName());
        ps.setInt(2,anime.getEpisodes());
        ps.setInt(3,anime.getProducer_id());
        ps.setInt(4,anime.getId());
        return ps;
    }

    public static Optional<Anime> findById(Integer id){
        log.info("Finding Anime by id '{}'",id);
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = createPrepareStatementFindById(conn,id);
            ResultSet rs = ps.executeQuery()){
            if(!rs.next()) return Optional.empty();
            return Optional.of(Anime
                    .builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .episodes(rs.getInt("episodes"))
                    .producer_id(rs.getInt("producer_id"))
                    .build());
        }catch (SQLException e){
            log.error("Error while trying to find all animes",e);
        }
        return Optional.empty();
    }

    private static PreparedStatement createPrepareStatementFindById(Connection conn,Integer id) throws SQLException {
        String sql = "SELECT * FROM anime_store.anime where id = ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,id);
        return ps;
    }


}
