package br.com.produtos.apirest.controllers;

import br.com.produtos.apirest.models.Produto;
import br.com.produtos.apirest.repository.ProdutoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Api(value = "API REST Produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/produtos")
    @ApiOperation(value = "Retorna uma lista de produtos")
    public List<Produto> listaProdutos(){
        return produtoRepository.findAll();
    }

    @GetMapping("/produto/{id}")
    @ApiOperation(value = "Retorna um produto")
    public ResponseEntity<Produto> listaProduto(@PathVariable(value = "id") Long id){
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        if(optionalProduto.isPresent()){
            return ResponseEntity.ok(optionalProduto.get());
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/produto")
    @Transactional
    @ApiOperation(value = "Adiciona um produto")
    public ResponseEntity<Produto> adicionaProduto(@Valid @RequestBody Produto produto){
        produtoRepository.save(produto);
        return ResponseEntity.ok(produto);
    }

    @PutMapping("/produto")
    @Transactional
    @ApiOperation(value = "Altera um produto")
    public ResponseEntity<Produto> alteraProduto(@Valid @RequestBody Produto produto){
        Optional<Produto> optionalProduto = produtoRepository.findById(produto.getId());
        if(optionalProduto.isPresent()){
            return ResponseEntity.ok(produtoRepository.save(produto));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/produto/{id}")
    @Transactional
    @ApiOperation(value = "Deleta um produto")
    public ResponseEntity deletaProduto(@PathVariable(value = "id") Long id){
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        if(optionalProduto.isPresent()){
            produtoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
