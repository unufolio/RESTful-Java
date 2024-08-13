package io.unufolio.restful;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Unufolio unufolio@gmail.com
 */
@RestController
@RequestMapping("things")
public class ThingController {

    private static final List<Thing> THINGS = Collections.synchronizedList(new ArrayList<>());
    private static final Map<Long, Thing> THINGS_BY_ID = Collections.synchronizedMap(new HashMap<>());

    static {
        THINGS.add(new Thing(1L, "Things-1", 10, "Things-1 description"));
        THINGS.add(new Thing(2L, "Things-2", 20, "Things-2 description"));
        THINGS.add(new Thing(3L, "Things-3", 30, "Things-3 description"));
        THINGS.add(new Thing(4L, "Things-4", 20, "Things-4 description"));
        THINGS.add(new Thing(5L, "Things-5", 30, "Things-5 description"));
        THINGS.add(new Thing(6L, "Things-6", 10, "Things-6 description"));
        THINGS.add(new Thing(7L, "Things-7", 20, "Things-7 description"));
        THINGS.add(new Thing(8L, "Things-8", 30, "Things-8 description"));
        THINGS.add(new Thing(9L, "Things-9", 10, "Things-9 description"));
        THINGS.add(new Thing(10L, "Things-10", 20, "Things-10 description"));
        THINGS.add(new Thing(11L, "Things-11", 30, "Things-11 description"));
        THINGS.add(new Thing(12L, "Things-12", 10, "Things-12 description"));
        THINGS.add(new Thing(13L, "Things-13", 20, "Things-13 description"));

        for (Thing thing : THINGS) {
            THINGS_BY_ID.put(thing.getId(), thing);
        }
    }

    @GetMapping
    public ResponseEntity<List<DynamicRepresentation>> list(PaginationParameters paginationParameters,
                                                            @RequestParam("fields") String fields) {
        paginationParameters = paginationParameters.limitPageNum(2).limitPageSize(10);
        long pageNum = paginationParameters.getPageNum();
        long pageSize = paginationParameters.getPageSize();

        List<String> fieldList = Arrays.asList(fields.split(","));

        int size = THINGS.size();

        int offset = (int) ((pageNum - 1) * pageSize);
        int toIndex = Math.min(size - 1, (int) (offset + pageSize));

        List<Thing> things = ThingController.THINGS.subList(offset, toIndex);

        DynamicRepresentationShaper dynamicRepresentationShaper =
                new DynamicRepresentationShaper(fieldList);

        List<DynamicRepresentation> list = new ArrayList<>();
        for (Thing thing : things) {
            DynamicRepresentation model = dynamicRepresentationShaper.toModel(thing);
            model.put("_links", createLinks(model, ThingDTO.class));
            list.add(model);
        }

        return ResponseEntity.ok(list);
    }

    @GetMapping("{id}")
    public ResponseEntity<DynamicRepresentation> retrieve(@PathVariable Long id,
                                                          @RequestParam("fields") String fields) {
        Thing thing = THINGS_BY_ID.get(id);

        List<String> fieldList = Arrays.asList(fields.split(","));

        DynamicRepresentationShaper dynamicRepresentationShaper =
                new DynamicRepresentationShaper(fieldList);

        DynamicRepresentation model = dynamicRepresentationShaper.toModel(thing);
        model.put("_links", createLinks(model, ThingDTO.class));
        return ResponseEntity.ok(model);
    }

    @PutMapping("{id}")
    public ResponseEntity<DynamicRepresentation> update(@PathVariable Long id,
                                                        @RequestBody ThingRequest request) {

        Thing thing = THINGS_BY_ID.get(id);

        if (thing == null) {
            Field[] declaredFields = ThingDTO.class.getDeclaredFields();
            String fields = Arrays.stream(declaredFields)
                    .map(Field::getName)
                    .collect(Collectors.joining(","));

            URI uri = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ThingController.class).retrieve(id, fields)).toUri();
            return ResponseEntity.created(uri).build();
        }

        DynamicRepresentationShaper dynamicRepresentationShaper =
                new DynamicRepresentationShaper(null);

        DynamicRepresentation model = dynamicRepresentationShaper.toModel(thing);
        model.put("_links", createLinks(model, ThingDTO.class));
        return ResponseEntity.ok(model);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ThingRequest request) {

        Long id = 13L;

        Field[] declaredFields = ThingDTO.class.getDeclaredFields();
        String fields = Arrays.stream(declaredFields)
                .map(Field::getName)
                .collect(Collectors.joining(","));

        URI uri = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ThingController.class).retrieve(id, fields)).toUri();
        return ResponseEntity.created(uri).build();
    }


    @PostMapping("{id}:action")
    public ResponseEntity<DynamicRepresentation> action(@PathVariable Long id,
                                                        @RequestBody ThingRequest request,
                                                        @RequestParam String action) {
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        THINGS.remove(THINGS_BY_ID.get(id));
        return ResponseEntity.ok().build();
    }

    private List<Link> createLinks(DynamicRepresentation model, Class clazz) {
        Long id = (Long) model.get("id");
        List<Link> links = new ArrayList<>();

        Field[] declaredFields = clazz.getDeclaredFields();
        String fields = Arrays.stream(declaredFields)
                .map(Field::getName)
                .collect(Collectors.joining(","));

        links.add(HateoasLink.of(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ThingController.class)
                .retrieve(id, fields)).withSelfRel()).withMethod("GET"));
        links.add(HateoasLink.of(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ThingController.class)
                .update(id, null)).withRel("update")).withMethod("PUT"));
        links.add(HateoasLink.of(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ThingController.class)
                .action(id, null, "action")).withRel("action")).withMethod("POST"));
        links.add(HateoasLink.of(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ThingController.class)
                .delete(id)).withRel("delete")).withMethod("DELETE"));
        return links;
    }

}
