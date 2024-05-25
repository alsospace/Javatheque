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

/**
 * This class implements the Codec interface to serialize and deserialize Library objects.
 */
public class LibraryCodec implements Codec<Library> {

    /**
     * Constructs a LibraryCodec object.
     */
    public LibraryCodec() {}

    /**
     * Encodes a Library object to BSON format.
     *
     * @param writer         The BsonWriter used for writing BSON.
     * @param library        The Library object to encode.
     * @param encoderContext The encoder context.
     */
    @Override
    public void encode(BsonWriter writer, Library library, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeString("library_id", library.getId());
        writer.writeString("owner_id", library.getOwnerId());
        writer.writeEndDocument();
    }

    /**
     * Decodes a BSON document to a Library object.
     *
     * @param reader          The BsonReader used for reading BSON.
     * @param decoderContext  The decoder context.
     * @return The decoded Library object.
     */
    @Override
    public Library decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        String libraryId = reader.readString("library_id");
        String ownerId = reader.readString("owner_id");
        reader.readEndDocument();
        FilmRepository filmRepository = new FilmRepository();
        List<Film> films = filmRepository.getFilmsByLibraryId(libraryId);
        return new Library(libraryId, ownerId, films);
    }

    /**
     * Gets the class of the objects that this codec can encode and decode.
     *
     * @return The class of Library.
     */
    @Override
    public Class<Library> getEncoderClass() {
        return Library.class;
    }
}