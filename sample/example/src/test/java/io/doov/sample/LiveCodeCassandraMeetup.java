/*
 * Copyright 2017 Courtanet
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.doov.sample;

import static com.datastax.driver.core.DataType.text;
import static com.datastax.driver.core.DataType.timeuuid;
import static com.datastax.driver.core.schemabuilder.SchemaBuilder.Direction.DESC;
import static io.doov.sample.field.SampleFieldId.EMAIL;
import static io.doov.sample.field.SampleFieldId.LOGIN;
import static java.util.stream.Collectors.toMap;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.CodecRegistry;
import com.datastax.driver.core.ColumnMetadata;
import com.datastax.driver.core.DataType;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.schemabuilder.Create;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;
import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.extras.codecs.enums.EnumNameCodec;
import com.datastax.driver.extras.codecs.jdk8.LocalDateCodec;

import io.doov.core.FieldId;
import io.doov.core.FieldInfo;
import io.doov.core.FieldModel;
import io.doov.sample.field.SampleTag;
import io.doov.sample.model.Account;
import io.doov.sample.model.Company;
import io.doov.sample.model.Country;
import io.doov.sample.model.EmailType;
import io.doov.sample.model.Language;
import io.doov.sample.model.SampleModel;
import io.doov.sample.model.SampleModels;
import io.doov.sample.model.Timezone;
import io.doov.sample.wrapper.SampleModelWrapper;

/**
 * Create a keyspace before starting the live code <tt>
 * CREATE KEYSPACE meetup WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 1};
 * </tt>
 */
public class LiveCodeCassandraMeetup {

    public static void main(String[] args) {
        intro();
        mixingWithMap();
        tagFiltering();

        cqlCreate();
        cqlInsert();
        cqlAlter();
    }

    static void intro() {
        SampleModel model = new SampleModel();
        model.setAccount(new Account());
        model.getAccount().setEmail("support@lesfurets.com");
        System.out.println(model.getAccount().getEmail());

        FieldModel fieldModel = new SampleModelWrapper(model);
        System.out.println(fieldModel.<String> get(EMAIL));

        fieldModel.set(EMAIL, "gdu@lesfurets.com");
        System.out.println(fieldModel.<String> get(EMAIL));
    }

    static void mixingWithMap() {
        FieldModel model = SampleModels.wrapper();
        System.out.println(model.<String> get(EMAIL));
        model.stream().forEach(System.out::println);

        Map<FieldId, Object> map = model.stream().filter(e -> Objects.nonNull(e.getValue()))
                        .collect(toMap(Entry::getKey, Entry::getValue));
        System.out.println(map);

        SampleModelWrapper newModel = map.entrySet().stream().collect(SampleModelWrapper.toFieldModel());
        newModel.stream().forEach(System.out::println);
        System.out.println(newModel.getModel().getAccount().getEmail());
    }

    static void tagFiltering() {
        FieldModel model = SampleModels.wrapper();

        Map<FieldId, Object> map = model.stream().collect(toMap(Entry::getKey, Entry::getValue));
        SampleModelWrapper newModel = map.entrySet().stream()
                        .filter(e -> e.getKey().hasTag(SampleTag.ACCOUNT))
                        // .filter(e -> e.getKey().hasTag(SampleTag.USER))
                        .collect(SampleModelWrapper.toFieldModel());

        newModel.stream().forEach(System.out::println);
    }

    static void cqlCreate() {
        FieldModel model = SampleModels.wrapper();
        Create create = SchemaBuilder.createTable("meetup", "sample_model")
                        .addPartitionKey(LOGIN.name(), text())
                        .addClusteringColumn("snapshot_id", timeuuid());

        model.getFieldInfos().stream().filter(f -> f.id() != LOGIN)
                        .forEach(f -> create.addColumn(f.id().code(), cqlType(f)));

        Create.Options createWithOptions = create.withOptions().clusteringOrder(LOGIN.name(), DESC);
        execute(createWithOptions);
    }

    static void cqlInsert() {
        FieldModel model = SampleModels.wrapper();
        Insert insert = QueryBuilder.insertInto("meetup", "sample_model");
        model.stream().forEach(e -> insert.value(e.getKey().code(), e.getValue()));
        insert.value("snapshot_id", UUIDs.timeBased());
        execute(insert);
    }

    static void cqlAlter() {
        FieldModel model = SampleModels.wrapper();
        model.getFieldInfos().stream().filter(f -> {
            ColumnMetadata column = cluster().getMetadata()
                            .getKeyspace("meetup").getTable("sample_model")
                            .getColumn(f.id().code());
            return column == null;
        }).forEach(f -> execute(SchemaBuilder.alterTable("meetup", "sample_model")
                        .addColumn(f.id().code()).type(cqlType(f))));
    }

    static void execute(Statement statement) {
        try (Cluster cluster = cluster(); Session session = cluster.connect()) {
            session.execute(statement);
        }
    }

    static Cluster cluster() {
        return new Cluster.Builder().addContactPoint("localhost").withCodecRegistry(codecRegistry()).build();
    }

    static CodecRegistry codecRegistry() {
        final CodecRegistry registry = new CodecRegistry();
        registry.register(LocalDateCodec.instance);
        registry.register(new EnumNameCodec<>(Country.class));
        registry.register(new EnumNameCodec<>(EmailType.class));
        registry.register(new EnumNameCodec<>(Language.class));
        registry.register(new EnumNameCodec<>(Timezone.class));
        registry.register(new EnumNameCodec<>(Company.class));
        return registry;
    }

    static DataType cqlType(FieldInfo info) {
        if (String.class.equals(info.type())) {
            return text();
        } else if (Boolean.class.equals(info.type()) || Boolean.TYPE.equals(info.type())) {
            return DataType.cboolean();
        } else if (Integer.class.equals(info.type()) || Integer.TYPE.equals(info.type())) {
            return DataType.cint();
        } else if (Double.class.equals(info.type()) || Double.TYPE.equals(info.type())) {
            return DataType.cdouble();
        } else if (Float.class.equals(info.type()) || Float.TYPE.equals(info.type())) {
            return DataType.cfloat();
        } else if (Long.class.equals(info.type()) || Long.TYPE.equals(info.type())) {
            return DataType.cint();
        } else if (LocalDate.class.equals(info.type())) {
            return DataType.date();
        } else if (Enum.class.isAssignableFrom(info.type())) {
            return DataType.text();
        } else if (Collection.class.isAssignableFrom(info.type())) {
            return DataType.set(text());
        }
        throw new IllegalArgumentException("unknown type " + info.type() + " for " + info.id());
    }
}
