package com.example.fragment_with_inline_fragment;

import com.apollographql.android.api.graphql.Field;
import com.apollographql.android.api.graphql.FragmentResponseFieldMapper;
import com.apollographql.android.api.graphql.Operation;
import com.apollographql.android.api.graphql.Query;
import com.apollographql.android.api.graphql.ResponseFieldMapper;
import com.apollographql.android.api.graphql.ResponseReader;
import com.example.fragment_with_inline_fragment.fragment.HeroDetails;
import com.example.fragment_with_inline_fragment.type.Episode;
import java.io.IOException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Generated("Apollo GraphQL")
public final class TestQuery implements Query<TestQuery.Data, Operation.Variables> {
  public static final String OPERATION_DEFINITION = "query TestQuery {\n"
      + "  hero {\n"
      + "    __typename\n"
      + "    name\n"
      + "    ...HeroDetails\n"
      + "    appearsIn\n"
      + "  }\n"
      + "}";

  public static final String QUERY_DOCUMENT = OPERATION_DEFINITION + "\n"
   + HeroDetails.FRAGMENT_DEFINITION;

  private final Operation.Variables variables;

  public TestQuery() {
    this.variables = Operation.EMPTY_VARIABLES;
  }

  @Override
  public String queryDocument() {
    return QUERY_DOCUMENT;
  }

  @Override
  public Operation.Variables variables() {
    return variables;
  }

  @Override
  public ResponseFieldMapper<? extends Operation.Data> responseFieldMapper() {
    return new Data.Mapper();
  }

  public static class Data implements Operation.Data {
    private final @Nullable Hero hero;

    public Data(@Nullable Hero hero) {
      this.hero = hero;
    }

    public @Nullable Hero hero() {
      return this.hero;
    }

    @Override
    public String toString() {
      return "Data{"
        + "hero=" + hero
        + "}";
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Data) {
        Data that = (Data) o;
        return ((this.hero == null) ? (that.hero == null) : this.hero.equals(that.hero));
      }
      return false;
    }

    @Override
    public int hashCode() {
      int h = 1;
      h *= 1000003;
      h ^= (hero == null) ? 0 : hero.hashCode();
      return h;
    }

    public static class Hero {
      private final @Nonnull String name;

      private final @Nonnull List<Episode> appearsIn;

      private final Fragments fragments;

      public Hero(@Nonnull String name, @Nonnull List<Episode> appearsIn, Fragments fragments) {
        this.name = name;
        this.appearsIn = appearsIn;
        this.fragments = fragments;
      }

      public @Nonnull String name() {
        return this.name;
      }

      public @Nonnull List<Episode> appearsIn() {
        return this.appearsIn;
      }

      public @Nonnull Fragments fragments() {
        return this.fragments;
      }

      @Override
      public String toString() {
        return "Hero{"
          + "name=" + name + ", "
          + "appearsIn=" + appearsIn + ", "
          + "fragments=" + fragments
          + "}";
      }

      @Override
      public boolean equals(Object o) {
        if (o == this) {
          return true;
        }
        if (o instanceof Hero) {
          Hero that = (Hero) o;
          return ((this.name == null) ? (that.name == null) : this.name.equals(that.name))
           && ((this.appearsIn == null) ? (that.appearsIn == null) : this.appearsIn.equals(that.appearsIn))
           && ((this.fragments == null) ? (that.fragments == null) : this.fragments.equals(that.fragments));
        }
        return false;
      }

      @Override
      public int hashCode() {
        int h = 1;
        h *= 1000003;
        h ^= (name == null) ? 0 : name.hashCode();
        h *= 1000003;
        h ^= (appearsIn == null) ? 0 : appearsIn.hashCode();
        h *= 1000003;
        h ^= (fragments == null) ? 0 : fragments.hashCode();
        return h;
      }

      public static class Fragments {
        private HeroDetails heroDetails;

        public Fragments(HeroDetails heroDetails) {
          this.heroDetails = heroDetails;
        }

        public @Nullable HeroDetails heroDetails() {
          return this.heroDetails;
        }

        @Override
        public String toString() {
          return "Fragments{"
            + "heroDetails=" + heroDetails
            + "}";
        }

        @Override
        public boolean equals(Object o) {
          if (o == this) {
            return true;
          }
          if (o instanceof Fragments) {
            Fragments that = (Fragments) o;
            return ((this.heroDetails == null) ? (that.heroDetails == null) : this.heroDetails.equals(that.heroDetails));
          }
          return false;
        }

        @Override
        public int hashCode() {
          int h = 1;
          h *= 1000003;
          h ^= (heroDetails == null) ? 0 : heroDetails.hashCode();
          return h;
        }

        public static final class Mapper implements FragmentResponseFieldMapper<Fragments> {
          final HeroDetails.Mapper heroDetailsFieldMapper = new HeroDetails.Mapper();

          @Override
          public @Nonnull Fragments map(ResponseReader reader, @Nonnull String conditionalType)
              throws IOException {
            HeroDetails heroDetails = null;
            if (conditionalType.equals(HeroDetails.TYPE_CONDITION)) {
              heroDetails = heroDetailsFieldMapper.map(reader);
            }
            return new Fragments(heroDetails);
          }
        }
      }

      public static final class Mapper implements ResponseFieldMapper<Hero> {
        final Fragments.Mapper fragmentsFieldMapper = new Fragments.Mapper();

        final Field[] fields = {
          Field.forString("name", "name", null, false),
          Field.forList("appearsIn", "appearsIn", null, false, new Field.ListReader<Episode>() {
            @Override public Episode read(final Field.ListItemReader reader) throws IOException {
              return Episode.valueOf(reader.readString());
            }
          }),
          Field.forConditionalType("__typename", "__typename", new Field.ConditionalTypeReader<Fragments>() {
            @Override
            public Fragments read(String conditionalType, ResponseReader reader) throws
                IOException {
              return fragmentsFieldMapper.map(reader, conditionalType);
            }
          })
        };

        @Override
        public Hero map(ResponseReader reader) throws IOException {
          final String name = reader.read(fields[0]);
          final List<Episode> appearsIn = reader.read(fields[1]);
          final Fragments fragments = reader.read(fields[2]);
          return new Hero(name, appearsIn, fragments);
        }
      }
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final Hero.Mapper heroFieldMapper = new Hero.Mapper();

      final Field[] fields = {
        Field.forObject("hero", "hero", null, true, new Field.ObjectReader<Hero>() {
          @Override public Hero read(final ResponseReader reader) throws IOException {
            return heroFieldMapper.map(reader);
          }
        })
      };

      @Override
      public Data map(ResponseReader reader) throws IOException {
        final Hero hero = reader.read(fields[0]);
        return new Data(hero);
      }
    }
  }
}
