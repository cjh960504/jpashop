package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.web.BookForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    /* 영속성 컨텍스트의 변경 감지를 활용한 Update*/
    /* 되도록 변경할 수 있는 필드들만 가져오는 것이 좋음 */
    @Transactional
    public void updateItem(Long itemId, String name, int price) {
        Item item = itemRepository.findOne(itemId);
        item.setName(name);
        item.setPrice(price);
    }
}
