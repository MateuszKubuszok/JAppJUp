/**
 * Copyright 2012-2013 Maciej Jaworski, Maimport static org.fest.assertions.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import com.autoupdater.client.download.ConnectionConfiguration;
://www.apache.org/licenses/LICENSE-2.0</p>
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.</p>
 */
package com.autoupdater.client.xml.parsers;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import com.autoupdater.client.download.ConnectionConfiguration;

public class TestAbstractXMLParser {
    @Test
    public void testGetXMLParser() {
        // given
        AbstractXMLParser<?> testParser = new AbstractXMLParserTester();

        // then
        assertThat(testParser.getXMLParser()).as("getXmlParser() should return SAXReader instance")
                .isNotNull().isInstanceOf(SAXReader.class);
    }

    @Test
    public void testGetContent() throws Exception {
        // given
        AbstractXMLParser<?> testParser = new AbstractXMLParserTester();

        // when
        String testContent = CorrectXMLExamples.genericXml;
        Element rootElement = new SAXReader()
                .read(new ByteArrayInputStream(testContent
                        .getBytes(ConnectionConfiguration.XML_ENCODING))).getRootElement();

        // then
        assertThat(testParser.getContent(rootElement)).as(
                "getContent(Element) should properly obtain element's content").isEqualTo(
                "test content");
    }
}
