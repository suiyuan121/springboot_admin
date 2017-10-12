package com.fuwo.b3d.learning.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by jin.zhang@fuwo.com on 2017/9/1.
 */
@Entity
@DiscriminatorValue("B")
public class BeginnerDocument extends Document {
}
