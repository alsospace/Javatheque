package fr.javatheque.database.codec;

import fr.javatheque.database.model.Film;
import fr.javatheque.database.model.Library;
import fr.javatheque.database.repository.FilmRepository;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.util.List;

public class LibraryCodec implements Codec<Library> {

    private final FilmRepository filmRepository;

    public LibraryCodec() {
        this.filmRepository = new FilmRepository();
    }

    @Override
    public void encode(BsonWriter writer, Library library, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeString("library_id", library.getId());
        writer.writeString("owner_id", library.getOwnerId());
        writer.writeEndDocument();
    }

    @Override
    public Library decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        String libraryId = reader.readString("library_id");
        String ownerId = reader.readString("owner_id");
        reader.readEndDocument();

        List<Film> films = filmRepository.getFilmsByLibraryId(libraryId);
        return new Library(libraryId, ownerId, films);
    }

    @Override
    public Class<Library> getEncoderClass() {
        return Library.class;
    }
}