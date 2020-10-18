package br.com.tagliaferrodev.dextra.pottertest.upload

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController("uploads")
class UploadController {

    @PostMapping("image")
    fun uploadFile(@RequestParam("arquivo") file: MultipartFile): ResponseEntity<String> {
        return ResponseEntity.ok("Recebido o arquivo ${file.name}")
    }
}