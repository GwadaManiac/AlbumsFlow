package com.lbc.myalbumlist.repo

import co.infinum.retromock.BodyFactory
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream


class ResourceBodyFactory : BodyFactory {
    @Throws(IOException::class)
    override fun create(input: String): InputStream {
        return FileInputStream(
            ResourceBodyFactory::class.java.getResource(input)?.file
        )
    }
}