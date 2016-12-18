package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer>{
	
	@Query("select f from Folder f where f.actor.id = ?1")
	Collection<Folder> foldersOfActor(int actorId);
	
	@Query("select f from Folder f where f.actor.id = ?1 and f.systemFolder = false")
	Collection<Folder> nonSystemFoldersOfActor(int actorId);
	
	@Query("select f from Folder f where (f.name= 'Trash box' and f.actor.id = ?1)")
	Folder foldersTrashOfActor(int actorId);
	
	@Query("select f from Folder f where (f.name= 'In box' and f.actor.id = ?1)")
	Folder foldersInBoxOfActor(int actorId);
	
	@Query("select f from Folder f where (f.name= 'Out box' and f.actor.id = ?1)")
	Folder foldersOutBoxOfActor(int actorId);
	
	@Query("select f from Folder f where (f.name= 'Spam box' and f.actor.id = ?1)")
	Folder foldersSpamBoxOfActor(int actorId);
}

