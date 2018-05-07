package org.javaee7.emr.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageDto implements Serializable {
	private static final int INFO = 0;
	private static final int CONF = 1;
	private static final int WARN = 2;
	private static final int ERROR = 3;
	private static final int FATAL = 4;

	private static final String INFO_CLASS = "info";
	private static final String CONF_CLASS = "conf";
	private static final String WARN_CLASS = "warn";
	private static final String ERROR_CLASS = "error";
	private static final String FATAL_CLASS = "fatal";

	private int level = INFO;
	private String styleClass = "";

	private List<Message> topMessages = new ArrayList<Message>();
	private List<Message> infoMessages = new ArrayList<Message>();
	private List<Message> confMessages = new ArrayList<Message>();
	private List<Message> warnMessages = new ArrayList<Message>();
	private List<Message> errorMessages = new ArrayList<Message>();
	private List<Message> fatalMessages = new ArrayList<Message>();

	private Map<String, String> comClass = new HashMap<String, String>();

	private String idOk = "";
	private String idCancel = "";
	private String idPromptInput = "";
	private String promptTitle = "";
	private String promptDefault = "";
	private boolean promptRequired = false;

	public MessageDto() {

	}

	public void showTop(String messageValue) {
		showTop(messageValue, null);
	}

	public void showTop(String messageValue, String messageLabel) {
		if (messageValue == null || messageValue.trim().isEmpty()) {
			return;
		}

		Message message = new Message();
		message.setMessage(messageValue);
		message.setLabel(messageLabel);
		topMessages.add(message);
	}

	/**
	 * Show message info
	 * 
	 * @param messageValue
	 */
	public void showInfo(String messageValue) {
		showInfo(messageValue, null, null, null);
	}

	public void showInfo(String messageValue, String messageLabel) {
		showInfo(messageValue, messageLabel, null, null);
	}

	public void showInfo(String messageValue, String messageLabel, String id) {
		showInfo(messageValue, messageLabel, null, null);
	}

	public void showInfo(String messageValue, String messageLabel, String id, String idOk) {
		if (messageValue == null || messageValue.trim().isEmpty()) {
			return;
		}

		level = level < INFO ? INFO : level;
		Message message = new Message();
		message.setLevel(INFO);
		message.setStyleClass(INFO_CLASS);
		message.setMessage(messageValue);
		message.setLabel(messageLabel);
		message.setId(id);
		infoMessages.add(message);

		if (id != null && !id.trim().isEmpty()) {
			comClass.put(id, INFO_CLASS);
		}
		this.idOk = idOk;
	}

	/**
	 * Show message confirm
	 * 
	 * @param messageValue
	 */
	public void showConf(String messageValue) {
		showConf(messageValue, null, null, null, null);
	}

	public void showConf(String messageValue, String messageLabel) {
		showConf(messageValue, messageLabel, null, null, null);
	}

	public void showConf(String messageValue, String messageLabel, String id) {
		showConf(messageValue, messageLabel, id, null, null);
	}

	public void showConf(String messageValue, String messageLabel, String id, String idOk) {
		showConf(messageValue, messageLabel, id, idOk, null);
	}

	public void showConf(String messageValue, String messageLabel, String id, String idOk, String idCancel) {
		if (messageValue == null || messageValue.trim().isEmpty()) {
			return;
		}

		level = level < CONF ? CONF : level;
		Message message = new Message();
		message.setLevel(CONF);
		message.setStyleClass(CONF_CLASS);
		message.setMessage(messageValue);
		message.setLabel(messageLabel);
		message.setId(id);
		confMessages.add(message);

		if (id != null && !id.trim().isEmpty()) {
			comClass.put(id, CONF_CLASS);
		}

		this.idOk = idOk;
		this.idCancel = idCancel;
	}

	/**
	 * Show message warning
	 * 
	 * @param messageValue
	 */
	public void showWarn(String messageValue) {
		showWarn(messageValue, null, null, null);
	}

	public void showWarn(String messageValue, String messageLabel) {
		showInfo(messageValue, messageLabel, null, null);
	}

	public void showWarn(String messageValue, String messageLabel, String id) {
		showWarn(messageValue, messageLabel, null, null);
	}

	public void showWarn(String messageValue, String messageLabel, String id, String idOk) {
		if (messageValue == null || messageValue.trim().isEmpty()) {
			return;
		}

		level = level < WARN ? WARN : level;
		Message message = new Message();
		message.setLevel(WARN);
		message.setStyleClass(WARN_CLASS);
		message.setMessage(messageValue);
		message.setLabel(messageLabel);
		message.setId(id);
		warnMessages.add(message);

		if (id != null && !id.trim().isEmpty()) {
			comClass.put(id, WARN_CLASS);
		}
		this.idOk = idOk;
	}

	/**
	 * Show message error
	 * 
	 * @param messageValue
	 */
	public void showError(String messageValue) {
		showError(messageValue, null, null, null);
	}

	public void showError(String messageValue, String messageLabel) {
		showError(messageValue, messageLabel, null, null);
	}

	public void showError(String messageValue, String messageLabel, String id) {
		showError(messageValue, messageLabel, null, null);
	}

	public void showError(String messageValue, String messageLabel, String id, String idOk) {
		if (messageValue == null || messageValue.trim().isEmpty()) {
			return;
		}

		level = level < ERROR ? ERROR : level;
		Message message = new Message();
		message.setLevel(ERROR);
		message.setStyleClass(ERROR_CLASS);
		message.setMessage(messageValue);
		message.setLabel(messageLabel);
		message.setId(id);
		errorMessages.add(message);

		if (id != null && !id.trim().isEmpty()) {
			comClass.put(id, ERROR_CLASS);
		}
		this.idOk = idOk;
	}

	/**
	 * Show message fatal
	 * 
	 * @param messageValue
	 */
	public void showFatal(String messageValue) {
		showFatal(messageValue, null, null, null);
	}

	public void showFatal(String messageValue, String messageLabel) {
		showFatal(messageValue, messageLabel, null, null);
	}

	public void showFatal(String messageValue, String messageLabel, String id) {
		showFatal(messageValue, messageLabel, null, null);
	}

	public void showFatal(String messageValue, String messageLabel, String id, String idOk) {
		if (messageValue == null || messageValue.trim().isEmpty()) {
			return;
		}

		level = level < FATAL ? FATAL : level;
		Message message = new Message();
		message.setLevel(FATAL);
		message.setStyleClass(FATAL_CLASS);
		message.setMessage(messageValue);
		message.setLabel(messageLabel);
		message.setId(id);
		fatalMessages.add(message);

		if (id != null && !id.trim().isEmpty()) {
			comClass.put(id, FATAL_CLASS);
		}
		this.idOk = idOk;
	}

	/**
	 * Prompt value
	 * 
	 * @param promptTitle
	 * @param idPromptInput
	 * @param idOk
	 */
	public void showPrompt(String idPromptInput, String promptTitle, String idOk) {
		showPrompt(idPromptInput, promptTitle, "", false, idOk, "");
	}

	public void showPrompt(String idPromptInput, String promptTitle, String promptDefault, String idOk) {
		showPrompt(idPromptInput, promptTitle, promptDefault, false, idOk, "");
	}

	public void showPrompt(String idPromptInput, String promptTitle, String promptDefault, boolean required,
			String idOk) {
		showPrompt(idPromptInput, promptTitle, promptDefault, required, idOk, "");
	}

	public void showPrompt(String idPromptInput, String promptTitle, String promptDefault, boolean required,
			String idOk, String idCancel) {
		if (idPromptInput == null || idPromptInput.trim().isEmpty()) {
			return;
		}

		this.idPromptInput = idPromptInput;
		this.promptTitle = promptTitle;
		this.promptDefault = promptDefault;
		this.promptRequired = required;
		this.idOk = idOk;
		this.idCancel = idCancel;
	}

	/**
	 * Display top message
	 * 
	 * @return
	 */
	public boolean isShowTop() {
		return !topMessages.isEmpty();
	}

	/**
	 * Show message system
	 * 
	 * @return
	 */
	public boolean isShowMessage() {
		return !infoMessages.isEmpty() || !confMessages.isEmpty() || !warnMessages.isEmpty() || !errorMessages.isEmpty()
				|| !fatalMessages.isEmpty();
	}

	/**
	 * Show prompt
	 * 
	 * @return
	 */
	public boolean isShowPrompt() {
		if (idPromptInput == null || idPromptInput.trim().isEmpty()) {
			return false;
		}

		return true;
	}

	public List<Message> getMessages() {
		List<Message> messages = new ArrayList<Message>();
		messages.addAll(fatalMessages);
		messages.addAll(errorMessages);
		messages.addAll(warnMessages);
		messages.addAll(confMessages);
		messages.addAll(infoMessages);

		return messages;
	}

	public void clearMessages() {
		topMessages.clear();
		fatalMessages.clear();
		errorMessages.clear();
		warnMessages.clear();
		confMessages.clear();
		infoMessages.clear();
		comClass.clear();
		level = INFO;
		styleClass = "";
		idOk = "";
		idCancel = "";
		idPromptInput = "";
		promptTitle = "";
		promptDefault = "";
		promptRequired = false;
	}

	public void copyMessages(MessageDto dto) {
		// if (dto == null) {
		// return;
		// }
		topMessages.addAll(dto.getTopMessages());
		fatalMessages.addAll(dto.getFatalMessages());
		errorMessages.addAll(dto.getErrorMessages());
		warnMessages.addAll(dto.getWarnMessages());
		confMessages.addAll(dto.getConfMessages());
		infoMessages.addAll(dto.getInfoMessages());
		comClass.putAll(dto.getComClass());
		if (level < dto.getLevel()) {
			level = dto.getLevel();
			styleClass = dto.getStyleClass();
		}
		idOk = idOk == null || idOk.isEmpty() ? dto.getIdOk() : idOk;
		idCancel = idCancel == null || idCancel.isEmpty() ? dto.getIdCancel() : idCancel;
		idPromptInput = idPromptInput == null || idPromptInput.isEmpty() ? dto.getIdPromptInput() : idPromptInput;
		promptTitle = dto.getPromptTitle();
		promptDefault = dto.getPromptDefault();
		promptRequired = dto.isPromptRequired();
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public List<Message> getTopMessages() {
		return topMessages;
	}

	public void setTopMessages(List<Message> topMessages) {
		this.topMessages = topMessages;
	}

	public List<Message> getInfoMessages() {
		return infoMessages;
	}

	public void setInfoMessages(List<Message> infoMessages) {
		this.infoMessages = infoMessages;
	}

	public List<Message> getConfMessages() {
		return confMessages;
	}

	public void setConfMessages(List<Message> confMessages) {
		this.confMessages = confMessages;
	}

	public List<Message> getWarnMessages() {
		return warnMessages;
	}

	public void setWarnMessages(List<Message> warnMessages) {
		this.warnMessages = warnMessages;
	}

	public List<Message> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<Message> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public List<Message> getFatalMessages() {
		return fatalMessages;
	}

	public void setFatalMessages(List<Message> fatalMessages) {
		this.fatalMessages = fatalMessages;
	}

	public String getIdOk() {
		return idOk;
	}

	public void setIdOk(String idOk) {
		this.idOk = idOk;
	}

	public String getIdCancel() {
		return idCancel;
	}

	public void setIdCancel(String idCancel) {
		this.idCancel = idCancel;
	}

	public Map<String, String> getComClass() {
		return comClass;
	}

	public void setComClass(Map<String, String> comClass) {
		this.comClass = comClass;
	}

	public String getIdPromptInput() {
		return idPromptInput;
	}

	public void setIdPromptInput(String idPromptInput) {
		this.idPromptInput = idPromptInput;
	}

	public String getPromptTitle() {
		return promptTitle;
	}

	public void setPromptTitle(String promptTitle) {
		this.promptTitle = promptTitle;
	}

	public String getPromptDefault() {
		return promptDefault;
	}

	public void setPromptDefault(String promptDefault) {
		this.promptDefault = promptDefault;
	}

	public boolean isPromptRequired() {
		return promptRequired;
	}

	public void setPromptRequired(boolean promptRequired) {
		this.promptRequired = promptRequired;
	}
}
