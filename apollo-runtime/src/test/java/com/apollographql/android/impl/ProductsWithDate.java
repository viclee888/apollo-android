package com.apollographql.android.impl;

import com.apollographql.android.api.graphql.Field;
import com.apollographql.android.api.graphql.Operation;
import com.apollographql.android.api.graphql.Query;
import com.apollographql.android.api.graphql.ResponseFieldMapper;
import com.apollographql.android.api.graphql.ResponseReader;
import com.apollographql.android.impl.type.CustomType;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Nonnull;

public final class ProductsWithDate implements Query<ProductsWithDate.Data, Operation.Variables> {
  public static final String OPERATION_DEFINITION = "query ProductsWithDate {\n"
      + "  shop {\n"
      + "    products(first: 10) {\n"
      + "      edges {\n"
      + "        node {\n"
      + "          title\n"
      + "          createdAt\n"
      + "        }\n"
      + "      }\n"
      + "    }\n"
      + "  }\n"
      + "}";

  public static final String QUERY_DOCUMENT = OPERATION_DEFINITION;

  private final Variables variables;

  public ProductsWithDate() {
    this.variables = Operation.EMPTY_VARIABLES;
  }

  @Override
  public String queryDocument() {
    return QUERY_DOCUMENT;
  }

  @Override
  public Variables variables() {
    return variables;
  }

  @Override public ResponseFieldMapper<? extends Operation.Data> responseFieldMapper() {
    return new Data.Mapper();
  }

  public static class Data implements Operation.Data {
    private @Nonnull Shop shop;

    public Data(@Nonnull Shop shop) {
      this.shop = shop;
    }

    public @Nonnull Shop shop() {
      return this.shop;
    }

    public static class Shop {
      private @Nonnull Product products;

      public Shop(@Nonnull Product products) {
        this.products = products;
      }

      public @Nonnull Product products() {
        return this.products;
      }

      public static class Product {
        private @Nonnull List<Edge> edges;

        public Product(@Nonnull List<Edge> edges) {
          this.edges = edges;
        }

        public @Nonnull List<Edge> edges() {
          return this.edges;
        }

        public static class Edge {
          private @Nonnull Node node;

          public Edge(@Nonnull Node node) {
            this.node = node;
          }

          public @Nonnull Node node() {
            return this.node;
          }

          public static class Node {
            private @Nonnull String title;

            private @Nonnull Date createdAt;

            public Node(@Nonnull String title, @Nonnull Date createdAt) {
              this.title = title;
              this.createdAt = createdAt;
            }

            public @Nonnull String title() {
              return this.title;
            }

            public @Nonnull Date createdAt() {
              return this.createdAt;
            }

            public static final class Mapper implements ResponseFieldMapper<Node> {
              final Field[] fields = {
                  Field.forString("title", "title", null, false),
                  Field.forCustomType("createdAt", "createdAt", null, false, CustomType.DATETIME)
              };

              @Override
              public Node map(ResponseReader reader) throws IOException {
                final String title = reader.read(fields[0]);
                final Date createdAt = reader.read(fields[1]);
                return new Node(title, createdAt);
              }
            }
          }

          public static final class Mapper implements ResponseFieldMapper<Edge> {
            final Node.Mapper nodeMapper = new Node.Mapper();

            final Field[] fields = {
                Field.forObject("node", "node", null, false, new Field.ObjectReader<Node>() {
                  @Override public Node read(final ResponseReader reader) throws IOException {
                    return nodeMapper.map(reader);
                  }
                })
            };

            @Override
            public Edge map(ResponseReader reader) throws IOException {
              final Node node = reader.read(fields[0]);
              return new Edge(node);
            }
          }
        }

        public static final class Mapper implements ResponseFieldMapper<Product> {
          final Edge.Mapper edgeMapper = new Edge.Mapper();

          final Field[] fields = {
              Field.forList("edges", "edges", null, false, new Field.ObjectReader<Edge>() {
                @Override public Edge read(final ResponseReader reader) throws IOException {
                  return edgeMapper.map(reader);
                }
              })
          };

          @Override
          public Product map(ResponseReader reader) throws IOException {
            final List<Edge> edges = reader.read(fields[0]);
            return new Product(edges);
          }
        }
      }

      public static final class Mapper implements ResponseFieldMapper<Shop> {
        final Product.Mapper productMapper = new Product.Mapper();

        final Field[] fields = {
            Field.forObject("products", "products", null, false, new Field.ObjectReader<Product>() {
              @Override public Product read(final ResponseReader reader) throws IOException {
                return productMapper.map(reader);
              }
            })
        };

        @Override
        public Shop map(ResponseReader reader) throws IOException {
          final Product products = reader.read(fields[0]);
          return new Shop(products);
        }
      }
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final Shop.Mapper shopMapper = new Shop.Mapper();

      final Field[] fields = {
          Field.forObject("shop", "shop", null, false, new Field.ObjectReader<Shop>() {
            @Override public Shop read(final ResponseReader reader) throws IOException {
              return shopMapper.map(reader);
            }
          })
      };

      @Override
      public Data map(ResponseReader reader) throws IOException {
        final Shop shop = reader.read(fields[0]);
        return new Data(shop);
      }
    }
  }
}
