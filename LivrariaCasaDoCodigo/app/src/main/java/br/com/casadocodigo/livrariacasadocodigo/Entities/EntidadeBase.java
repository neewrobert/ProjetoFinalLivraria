package br.com.casadocodigo.livrariacasadocodigo.Entities;

import java.io.Serializable;

/**
 * Created by nrdossantos on 02/11/2017.
 */

public abstract class EntidadeBase  implements Serializable{

    protected Long id;

    public abstract Long getId();

    public abstract void setId(Long id);
}
