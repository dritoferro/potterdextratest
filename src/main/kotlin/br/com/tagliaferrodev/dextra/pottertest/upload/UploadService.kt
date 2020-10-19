package br.com.tagliaferrodev.dextra.pottertest.upload

import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.InlineDataPart
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class UploadService(
        private val http: FuelManager
) {

    private val url = "https://potter-dextratest.herokuapp.com/api/v1/uploads/image"

    fun upload(file: MultipartFile, texto: String) {
        val tempFile = createTempFile("upload", suffix = ".jpeg")
        file.transferTo(tempFile)

        val result = http.upload(url)
                .add(FileDataPart(file = tempFile, name = "arquivo"))
                .add(InlineDataPart(content = texto, name = "json"))
                .responseString()

        println(result)
    }
}