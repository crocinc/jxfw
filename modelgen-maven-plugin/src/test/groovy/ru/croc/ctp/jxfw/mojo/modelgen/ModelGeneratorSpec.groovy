package ru.croc.ctp.jxfw.mojo.modelgen

import org.apache.maven.plugin.logging.Log
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors

import static java.io.File.separator

class ModelGeneratorSpec extends Specification {

	def "generate xtend" () {
		given:
		def log = Mock(Log)
		def modelFileName = "out-model.xfwmm"
		def processor = new ModelGenerator(new File("src${separator}test${separator}resources${separator}${modelFileName}"),
				new File("target${separator}test${separator}resources"), "ru.croc.test", log)

		when:
		processor.process()

		then:
		1 * log.info(/Generated file: target${separator}test${separator}resources${separator}ru${separator}croc${separator}test${separator}datamodel${separator}datamodel.xtend/)
		
		Files.lines(new File(/target${separator}test${separator}resources${separator}ru${separator}croc${separator}test${separator}datamodel${separator}datamodel.xtend/).toPath()).limit(84).collect(Collectors.joining('\n')) == '''
|/*
    File was generated by CROC jXFW
*/
//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

package ru.croc.test.datamodel

import java.util.Set
import java.util.EnumSet
import javax.persistence.Column
import javax.persistence.JoinTable
import javax.persistence.Table
import javax.validation.constraints.Pattern
import ru.croc.ctp.jxfw.core.domain.meta.XFWElementLabel
import ru.croc.ctp.jxfw.core.domain.meta.persistence.XFWOneToMany
import ru.croc.ctp.jxfw.core.domain.meta.persistence.XFWManyToOne
import ru.croc.ctp.jxfw.core.generator.meta.XFWObject
import java.sql.Blob
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

@XFWObject
@XFWElementLabel(value = "Хозяйствующий субъект", lang = "ru")
@Table(name="agt_1")
class CCDTBusinessEntityDetailsType {
    @XFWElementLabel(value = "Код страны", lang = "ru")
    @XFWManyToOne(optional=true)
    CSDTQualifiedCodeType UnifiedCountryCode

    @XFWElementLabel(value = "Наименование хозяйствующего субъекта", lang = "ru")
    @Column(nullable=true, length=300)
    String BusinessEntityName

    @XFWElementLabel(value = "Краткое наименование хозяйствующего субъекта", lang = "ru")
    @Column(nullable=true, length=120)
    String BusinessEntityBriefName

    @XFWElementLabel(value = "Код организационно-правовой формы", lang = "ru")
    @XFWManyToOne(optional=true)
    CSDTQualifiedCodeType BusinessEntityTypeCode

    @XFWElementLabel(value = "Наименование организационно-правовой формы", lang = "ru")
    @Column(nullable=true, length=300)
    String BusinessEntityTypeName

    @XFWElementLabel(value = "Идентификатор хозяйствующего субъекта", lang = "ru")
    @XFWManyToOne(optional=true)
    CSDTBusinessEntityIdType BusinessEntityId

    @XFWElementLabel(value = "Уникальный идентификационный таможенный номер", lang = "ru")
    @Column(nullable=true, length=17)
    String UniqueCustomsNumberId

    @XFWElementLabel(value = "Идентификатор налогоплательщика", lang = "ru")
    @Column(nullable=true, length=20)
    String TaxpayerId

    @XFWElementLabel(value = "Код причины постановки на учет", lang = "ru")
    @Pattern(regexp = "^.{9}$", message = "Значение свойства не соответствует заданному шаблону")
    @Column(nullable=true)
    String TaxRegistrationReasonCode

    @XFWElementLabel(value = "Адрес", lang = "ru")
    @XFWOneToMany
    @JoinTable(name = "tableName")
    Set<CCDTSubjectAddressDetailsType> SubjectAddressDetails

    @XFWElementLabel(value = "Контактный реквизит", lang = "ru")
    @XFWOneToMany
    Set<CCDTCommunicationDetailsType> CommunicationDetails

}

@XFWObject
@XFWElementLabel(value = "Контактный реквизит", lang = "ru")
class CCDTCommunicationDetailsType {'''.replaceFirst("\n","").stripMargin()

	}


	/*
	TODO JXFW-904 В результирующем xtend должно быть так:
	    @XFWManyToOne(optional=false)
		    MessageType messageType
	Пока оставляю правльную генерацию обязательности для модели, полученной из трансформатора.

	*/
	def "generate xtend new model structure" () {
		given:
		def log = Mock(Log)
		def modelFileName = "XFWModel.ecore"
		File modelFile = new File("src${separator}test${separator}resources${separator}${modelFileName}")
		Path target = Paths.get("target")
		def processorEnums = new EnumGenerator(modelFile, target, "", new HashMap<>());

		def processor = new ModelGenerator(modelFile,
				target.toFile(), "", log)

		when:
		processor.process()
		processorEnums.generate()

		then:
		1 * log.info(/Generated file: target${separator}ru${separator}croc${separator}ctp${separator}integration${separator}domain${separator}jxfwmodel.xtend/)

		Files.lines(new File(/target${separator}ru${separator}croc${separator}ctp${separator}integration${separator}domain${separator}jxfwmodel.xtend/).toPath()).limit(86).collect(Collectors.joining('\n')) == '''
|/*
    File was generated by CROC jXFW
*/
//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

package ru.croc.ctp.integration.domain

import java.util.Set
import java.util.EnumSet
import javax.persistence.Column
import javax.persistence.JoinTable
import javax.persistence.Table
import javax.validation.constraints.Pattern
import ru.croc.ctp.jxfw.core.domain.meta.XFWElementLabel
import ru.croc.ctp.jxfw.core.domain.meta.persistence.XFWOneToMany
import ru.croc.ctp.jxfw.core.domain.meta.persistence.XFWManyToOne
import ru.croc.ctp.jxfw.core.generator.meta.XFWObject
import java.sql.Blob
import java.time.LocalDateTime

@XFWObject
class InboundMessage extends Message {
}

@XFWObject
abstract class Message {
    @XFWManyToOne(optional=true)
    MessageType messageType

    @Column(nullable=true)
    LocalDateTime creationDate

    @Column(nullable=true)
    MessageStatus status

    @Column(nullable=true)
    LocalDateTime statusDate

    @Column(nullable=true)
    LocalDateTime expirationDate

}

@XFWObject
class MessageArchive extends Message {
    @Column(nullable=true)
    String messageId

    @Column(nullable=true)
    String inboundMessageId

}

@XFWObject
class MessageBody {
    @Column(nullable=true)
    String messageId

    @Column(nullable=true)
    Blob body

}

@XFWObject
class MessageType {
    @Column(nullable=true)
    String name

    @Column(nullable=true)
    String handler

}

@XFWObject
class OutboundMessage extends Message {
    @Column(nullable=true)
    String inboundMessageId

}'''.replaceFirst("\n","").stripMargin()

		Files.lines(new File(/target${separator}ru${separator}croc${separator}ctp${separator}integration${separator}domain${separator}MessageStatus.java/).toPath()).collect(Collectors.joining('\n')) == '''
|package ru.croc.ctp.integration.domain;

import java.lang.Override;
import java.lang.String;
import ru.croc.ctp.jxfw.core.domain.XfwCodeEnum;
import ru.croc.ctp.jxfw.core.domain.meta.XFWEnumId;
import ru.croc.ctp.jxfw.core.metamodel.runtime.XfwModelFactory;
import ru.croc.ctp.jxfw.metamodel.runtime.XfwEnumeration;

public enum MessageStatus implements XfwCodeEnum<String> {
  @XFWEnumId(1)
  NEW("NEW"),

  @XFWEnumId(2)
  PROCESSING("PROCESSING"),

  @XFWEnumId(4)
  COMPLETE("COMPLETE"),

  @XFWEnumId(8)
  ERROR("ERROR");

  public static final XfwEnumeration METADATA = XfwModelFactory.getInstance().findEnum(MessageStatus.class);

  private final String code;

  MessageStatus(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return this.code;
  }
}'''.replaceFirst("\n","").stripMargin()


	}















	def "generate xtend new model structure flags" () {
		given:
		def log = Mock(Log)
		def modelFileName = "XFWModelFlags.ecore"
		File modelFile = new File("src${separator}test${separator}resources${separator}${modelFileName}")
		Path target = Paths.get("target")
		def processorEnums = new EnumGenerator(modelFile, target, "", new HashMap<>());

		def processor = new ModelGenerator(modelFile,
				target.toFile(), "", log)

		when:
		processor.process()
		processorEnums.generate()

		then:
		1 * log.info(/Generated file: target${separator}ru${separator}croc${separator}ctp${separator}integration${separator}domain${separator}jxfwmodel.xtend/)

		Files.lines(new File(/target${separator}ru${separator}croc${separator}ctp${separator}integration${separator}domain${separator}jxfwmodel.xtend/).toPath()).limit(86).collect(Collectors.joining('\n')) == '''
|/*
    File was generated by CROC jXFW
*/
//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

package ru.croc.ctp.integration.domain

import java.util.Set
import java.util.EnumSet
import javax.persistence.Column
import javax.persistence.JoinTable
import javax.persistence.Table
import javax.validation.constraints.Pattern
import ru.croc.ctp.jxfw.core.domain.meta.XFWElementLabel
import ru.croc.ctp.jxfw.core.domain.meta.persistence.XFWOneToMany
import ru.croc.ctp.jxfw.core.domain.meta.persistence.XFWManyToOne
import ru.croc.ctp.jxfw.core.generator.meta.XFWObject
import java.sql.Blob
import java.time.LocalDateTime

@XFWObject
class InboundMessage extends Message {
}

@XFWObject
abstract class Message {
    @XFWManyToOne(optional=true)
    MessageType messageType

    @Column(nullable=true)
    LocalDateTime creationDate

    @Column(nullable=true)
    EnumSet<MessageStatus> status

    @Column(nullable=true)
    LocalDateTime statusDate

    @Column(nullable=true)
    LocalDateTime expirationDate

}

@XFWObject
class MessageArchive extends Message {
    @Column(nullable=true)
    String messageId

    @Column(nullable=true)
    String inboundMessageId

}

@XFWObject
class MessageBody {
    @Column(nullable=true)
    String messageId

    @Column(nullable=true)
    Blob body

}

@XFWObject
class MessageType {
    @Column(nullable=true)
    String name

    @Column(nullable=true)
    String handler

}

@XFWObject
class OutboundMessage extends Message {
    @Column(nullable=true)
    String inboundMessageId

}'''.replaceFirst("\n","").stripMargin()

		Files.lines(new File(/target${separator}ru${separator}croc${separator}ctp${separator}integration${separator}domain${separator}MessageStatus.java/).toPath()).collect(Collectors.joining('\n')) == '''
|package ru.croc.ctp.integration.domain;

import java.lang.Override;
import java.lang.String;
import ru.croc.ctp.jxfw.core.domain.XfwCodeEnum;
import ru.croc.ctp.jxfw.core.domain.meta.XFWEnumId;
import ru.croc.ctp.jxfw.core.metamodel.runtime.XfwModelFactory;
import ru.croc.ctp.jxfw.metamodel.runtime.XfwEnumeration;

public enum MessageStatus implements XfwCodeEnum<String> {
  @XFWEnumId(1)
  NEW("NEW"),

  @XFWEnumId(2)
  PROCESSING("PROCESSING"),

  @XFWEnumId(4)
  COMPLETE("COMPLETE"),

  @XFWEnumId(8)
  ERROR("ERROR");

  public static final XfwEnumeration METADATA = XfwModelFactory.getInstance().findEnum(MessageStatus.class);

  private final String code;

  MessageStatus(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return this.code;
  }
}'''.replaceFirst("\n","").stripMargin()


	}








}


