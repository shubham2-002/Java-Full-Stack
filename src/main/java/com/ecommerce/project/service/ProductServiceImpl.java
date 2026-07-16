package com.ecommerce.project.service;

import com.ecommerce.project.Repository.CategoryRepo;
import com.ecommerce.project.Repository.ProductRepo;
import com.ecommerce.project.exceptions.APIExcpetion;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;



@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepo productRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    FileService fileService;

    @Value("${project.image}")
    private String path;

    @Override
    public ProductDTO addProduct(Product product, Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()->
                new ResourceNotFoundException("Category","categoryId",categoryId));

        boolean isProductNotPresent = true;

        List<Product> products= category.getProducts();
        for (Product value : products) {
            if (value.getProductName().equals(product.getProductName())) {
                isProductNotPresent = false;
                break;
            }
        }
        if (isProductNotPresent) {
        product.setCategory(category);
        product.setImg("defalut.png");
        Double specialPrice = product.getPrice()-
                ((product.getDiscount() *0.01)*product.getPrice());
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepo.save(product);
        return modelMapper.map(savedProduct,ProductDTO.class);
        }
        else {
            throw new APIExcpetion("Product Already Exists!!!!");
        }
    }

    @Override
    public ProductResponse getAllProduct() {
        return null;
    }


    @Override
    public ProductResponse getAllProduct(Integer pageNumber, Integer pageSize,String sortBy,String sortOrder){
        Sort sortByOrder = sortOrder.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pagedetails =  PageRequest.of(pageNumber,pageSize,sortByOrder);
        Page<Product> productPage=productRepo.findAll(pagedetails);


      List<Product> products= productPage.getContent();
      List<ProductDTO>productDTOS = products.stream().map((product)->
              modelMapper.map(product,ProductDTO.class)).toList();

      if(products.isEmpty()){
          throw  new APIExcpetion("Product Not Found!!!!");
      }

      ProductResponse productResponse = new ProductResponse();
      productResponse.setContent(productDTOS);
      productResponse.setPageNumber(productPage.getNumber());
      productResponse.setPageSize(productPage.getSize());
      productResponse.setTotalPages(productPage.getTotalElements());
      productResponse.setTotalElements(productPage.getTotalElements());
      productResponse.setLastPage(productPage.isLast());
      return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()->
                new ResourceNotFoundException("Category","categoryId",categoryId));

        Sort sortByOrder = sortOrder.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pagedetails =  PageRequest.of(pageNumber,pageSize,sortByOrder);
        Page<Product> productPage=productRepo.findByCategoryOrderByPriceAsc(category,pagedetails);


        List<Product> products= productPage.getContent();

        List<ProductDTO>productDTOS = products.stream().map((product)->
                modelMapper.map(product,ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);

        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalPages(productPage.getTotalElements());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setLastPage(productPage.isLast());

        return productResponse;

    }

    @Override
    public ProductResponse searchProductByKey(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByOrder = sortOrder.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pagedetails =  PageRequest.of(pageNumber,pageSize,sortByOrder);
        Page<Product> productPage=productRepo.findByProductNameLikeIgnoreCase('%'+keyword+'%',pagedetails);


        List<Product> products= productPage.getContent();

        List<ProductDTO> productDTOS = products.stream().map(product->
                modelMapper.map(product,ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);

        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalPages(productPage.getTotalElements());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setLastPage(productPage.isLast());


        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
        Product productDB= productRepo.findById(productId).orElseThrow(()->
                new ResourceNotFoundException("Product","productId",productId));
        productDB.setProductName(productDTO.getProductName());
        productDB.setDescription(productDTO.getDescription());
        productDB.setPrice(productDTO.getPrice());
        productDB.setQuantity(productDTO.getQuantity());
        productDB.setDiscount(productDTO.getDiscount());
        productDB.setSpecialPrice(productDTO.getSpecialPrice());

        Product savedProduct= productRepo.save(productDB);

        return modelMapper.map(savedProduct,ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product productDb = productRepo.findById(productId).orElseThrow(()->
                new ResourceNotFoundException("Product","productId",productId));

        productRepo.delete(productDb);
        return modelMapper.map(productDb,ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImg(Long productId, MultipartFile image) throws IOException {
        //Getting product from DB

        Product productFromDB= productRepo.findById(productId).orElseThrow(
                ()->new ResourceNotFoundException("Product","productId",productId));

        // uplod to server and get image name

        String fileName =fileService.uploadImage(path,image);
        productFromDB.setImg(fileName);

        Product upadtedProduct= productRepo.save(productFromDB);
        return modelMapper.map(upadtedProduct,ProductDTO.class);
    }




}
