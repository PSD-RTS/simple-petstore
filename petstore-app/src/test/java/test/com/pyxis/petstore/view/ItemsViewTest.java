package test.com.pyxis.petstore.view;

import org.junit.Test;
import org.springframework.ui.ModelMap;
import test.support.com.pyxis.petstore.builders.EntityBuilder;

import java.util.Map;

import static com.pyxis.matchers.dom.DomMatchers.*;
import static com.threelevers.css.DocumentBuilder.dom;
import static org.hamcrest.MatcherAssert.assertThat;
import static test.support.com.pyxis.petstore.builders.Entities.entities;
import static test.support.com.pyxis.petstore.builders.ItemBuilder.anItem;
import static test.support.com.pyxis.petstore.velocity.VelocityRendering.render;

public class ItemsViewTest {

    String ITEMS_VIEW = "items";
    String keyword = "Iguana";
    String renderedPage;

    @Test public void
    notifiesWhenInventoryIsEmpty() {
        renderedPage = renderItemsPageUsing(anEmptyModel());

        assertThat(dom(renderedPage), hasUniqueSelector("#out-of-stock"));
        assertThat(dom(renderedPage), hasNoSelector("#items"));
    }

    @Test public void
    displaysNumberOfItemsAvailable() {
        renderedPage = renderItemsPageUsing(aModelWith(anItem(), anItem()));
        assertThat(dom(renderedPage), hasUniqueSelector("#inventory-count", withText("2")));
        assertThat(dom(renderedPage), hasSelector("#items tr.item", withSize(2)));
    }

    @Test public void
    displaysColumnHeadingsOnItemsTable() {
        renderedPage = renderItemsPageUsing(aModelWith(anItem()));
        assertThat(dom(renderedPage),
                hasSelector("#items th",
                        inOrder(withText("Reference number"),
                                withText("Description"),
                                withText("Price"))));
    }

    @Test public void
    displaysProductDetailsInColumns() throws Exception {
        Map<String, ?> model = aModelWith(anItem().
                withNumber("12345678").
                describedAs("Green Adult").
                priced("18.50"));

        renderedPage = renderItemsPageUsing(model);
        assertThat(dom(renderedPage),
                hasSelector("#items td",
                        inOrder(withText("12345678"),
                                withText("Green Adult"),
                                withText("18.50"))));
    }

    private Map<String, ?> anEmptyModel() {
        return aModelWith();
    }

    private Map<String, ?> aModelWith(EntityBuilder<?>... entityBuilders) {
        ModelMap model = new ModelMap();
        model.addAttribute("keyword", keyword);
        model.addAttribute("itemList", entities(entityBuilders));
        return model;
    }

    private String renderItemsPageUsing(Map<String, ?> model) {
        return render(ITEMS_VIEW).using(model);
    }
}