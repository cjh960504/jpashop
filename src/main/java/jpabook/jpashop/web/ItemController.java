package jpabook.jpashop.web;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping(value = "/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "/items/createItemForm";
    }

    @PostMapping(value = "/items/new")
    public String create(BookForm form) { //현재 validation 사항이 없으므로 바로 BookForm?
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setIsbn(form.getIsbn());
        book.setAuthor(form.getAuthor());

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping(value = "/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping(value = "/items/{itemId}/edit")
    public String updateItemForm(@PathVariable Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);

        BookForm bookForm = new BookForm();
        bookForm.setId(item.getId());
        bookForm.setStockQuantity(item.getStockQuantity());
        bookForm.setAuthor(item.getAuthor());
        bookForm.setIsbn(item.getIsbn());
        bookForm.setName(item.getName());
        bookForm.setPrice(item.getPrice());

        model.addAttribute("form", bookForm);
        return "items/updateItemForm";
    }

    @PostMapping(value = "/items/{itemId}/edit")
    public String update(@ModelAttribute("form") BookForm form) {
        /* @ModelAttribute - BookForm 객체를 자동생성하고 파라미터의 이름을 이용하여 값을 자동 매핑한다. (getter/setter)
        *  Java beans 규칙(Getter, Setter, 생성자 포함)에 맞는 객체는 파라미터 전달이 가능하다.
        * */
        //준영속 상태의 Book 엔티티 생성 => 변경감지 X
        /*
            Book book = new Book();
            book.setId(form.getId());
            book.setName(form.getName());
            book.setPrice(form.getPrice());
            book.setStockQuantity(form.getStockQuantity());
            book.setAuthor(form.getAuthor());
            book.setIsbn(form.getIsbn());

            itemService.saveItem(book);
        */
        //영속성 엔티티 조회 후, 변경감지를 이용한 Update
        itemService.updateItem(form.getId(), form.getName(), form.getPrice());

        return "redirect:/items";
    }
}
