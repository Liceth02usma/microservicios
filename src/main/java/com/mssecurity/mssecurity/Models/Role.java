package com.mssecurity.mssecurity.Models;

//ayuda con configuración de los atributos
import lombok.Data;

//cada que se cree un objeto en mongo se llama documento
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document()

public class Role {

    //Atributos de la clase
    @Id//identificador del objeto (primaryKey)
    private String _id;
    private String name;
    private String description;

    //constructor (click derecho, constructor sin el id)
    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    //controladores
    public String get_id() {
        return _id;
    }

    //no necesitamos el set de id; por que el identificador no se puede modificar

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
